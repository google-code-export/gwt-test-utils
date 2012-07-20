package com.googlecode.gwt.test.internal;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.util.log.PrintWriterTreeLogger;

/**
 * The holder for the shared {@link TreeLogger} instance. If no custom logger is
 * set, a default one which prints in {@link System#out} is used. <strong>For
 * internal use only.</strong>
 * 
 * @author Gael Lazzari
 * 
 */
public class TreeLoggerHolder {

   private static final TreeLogger DEFAULT = new PrintWriterTreeLogger();

   private static TreeLogger treeLogger;

   /**
    * Gets the TreeLogger to use. Never null.
    * 
    * @return the singleton instance
    */
   public static TreeLogger getTreeLogger() {
      if (treeLogger == null) {
         treeLogger = DEFAULT;
      }

      return treeLogger;
   }

   /**
    * Set the TreeLogger to use. Cannot be null.
    * 
    * @param treeLogger
    */
   public void setTreeLogger(TreeLogger treeLogger) {
      assert treeLogger != null : "Custom TreeLogger cannot be null";

      TreeLoggerHolder.treeLogger = treeLogger;
   }

}
