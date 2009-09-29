import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import com.google.gwt.dev.HostedMode;

public class Launcher {

	private static final int BUFFER_SIZE = 8192;

	public static void main(String args[]) throws Exception {
		// Création du document DOM à partir du fichier pom.xml
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document pom = builder.parse(new File("pom.xml"));

		// Récupération des valeurs d'éléments nécessaires via Xpath
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		String moduleName = getXpathValue(pom, xpath, 
				"/project/build/plugins/plugin[artifactId='gwt-maven-plugin']/configuration/module");
		String startupUrl = getXpathValue(pom, xpath, 
				"/project/build/plugins/plugin[artifactId='gwt-maven-plugin']/configuration/runTarget");
		String hosted = getXpathValue(pom, xpath, 
				"/project/build/plugins/plugin[artifactId='gwt-maven-plugin']/configuration/hostedWebapp");

		System.out.println("Used module name : " + moduleName);
		System.out.println("Startup URL : " + startupUrl);
		System.out.println("Hosted war directory : " + hosted);

		// Copie récursive de tous les fichiers du répertoire par défaut "webapp"
		// dans le répertoire configuré par l'élement <hostedWebapp> du plugin
		copyTree(new File("src/main/webapp"), new File(hosted));

		// Lancement du hostedMode avec l'ensemble des données du plugin
		HostedMode.main(new String[] { "-war", hosted, "-startupUrl", startupUrl, moduleName });
	}

	private static String getXpathValue(Document source, XPath xpath, 
			String XpathExpr) throws XPathExpressionException {
		XPathExpression expr = xpath.compile(XpathExpr);
		return expr.evaluate(source);
	}

	private static void copyTree(File source, File target) throws IOException {
		if (source.isDirectory()) {
			mkdirs(target);
			File[] files = source.listFiles();
			for (File file : files) {
				String name = file.getName();
				if (!name.equalsIgnoreCase(".svn"))
					copyTree(new File(source, name), new File(target, name));
			}
		} else {
			copyFile(source, target);
		}
	}

	private static void mkdirs(File folder) throws IOException {
		if (folder != null && !folder.exists()) {
			if (!folder.mkdirs()) {
				throw new IOException("Unable to create directory " 
						+ folder.getAbsolutePath());
			}
		}
	}

	private static void copy(InputStream input, OutputStream output,
			byte buffer[]) throws IOException {
		int n;
		while ((n = input.read(buffer)) != -1) {
			output.write(buffer, 0, n);
		}
	}

	private static void copyFile(File source, File target)
	throws IOException {
		mkdirs(target.getParentFile());
		FileInputStream input = null;
		BufferedOutputStream output = null;

		try {
			input = new FileInputStream(source);
			output = new BufferedOutputStream(new FileOutputStream(target));
			copy(input, output, new byte[BUFFER_SIZE]);
		}
		finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					//never happen
					e.printStackTrace();
				}
			}
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					//never happen
					e.printStackTrace();
				}
			}
		}
	}
}
