package se.fishtank.css.selectors.dom;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;

/**
 * Helper methods for DOM operations.
 * 
 * @author Christer Sandberg
 * @author Gael Lazzari
 */
public class DOMHelper {

	/**
	 * Private CTOR.
	 */
	private DOMHelper() {
	}

	/**
	 * Get the first child node that is an element node.
	 * 
	 * @param node
	 *            The node whose children should be iterated.
	 * @return The first child element or {@code null}.
	 */
	public static Element getFirstChildElement(Node node) {
		NodeList<Node> children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.getItem(i).getNodeType() == Node.ELEMENT_NODE) {
				return (Element) children.getItem(i);
			}
		}

		return null;
	}

	/**
	 * Get the next sibling element.
	 * 
	 * @param node
	 *            The start node.
	 * @return The next sibling element or {@code null}.
	 */
	public static final Element getNextSiblingElement(Node node) {
		Node n = node.getNextSibling();
		while (n != null && n.getNodeType() != Node.ELEMENT_NODE) {
			n = n.getNextSibling();
		}

		return (Element) n;
	}

	/**
	 * Get the previous sibling element.
	 * 
	 * @param node
	 *            The start node.
	 * @return The previous sibling element or {@code null}.
	 */
	public static final Element getPreviousSiblingElement(Node node) {
		Node n = node.getPreviousSibling();
		while (n != null && n.getNodeType() != Node.ELEMENT_NODE) {
			n = n.getPreviousSibling();
		}

		return (Element) n;
	}

}
