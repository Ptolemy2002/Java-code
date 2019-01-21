package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.filechooser.FileSystemView;

import mslinks.ShellLink;

public class Main {

	/**
	 * Export resource in jar to external resource. The destination file will have
	 * the same name as the original.
	 * 
	 * @param resourceName
	 * @param destination
	 * @return
	 * @throws Exception
	 */
	static public String ExportResource(String resourceName, String destination) throws Exception {
		InputStream stream = null;
		OutputStream resStreamOut = null;
		try {
			stream = Main.class.getResourceAsStream(resourceName);// note that each / is a directory down in the "jar
																	// tree" been the jar the root of the tree
			if (stream == null) {
				throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
			}
			System.out.println("Got resource \"" + resourceName + "\" from jar file.");

			int readBytes;
			byte[] buffer = new byte[4096];

			File outputFile = new File(destination);
			outputFile.getParentFile().mkdirs();
			outputFile.createNewFile();

			resStreamOut = new FileOutputStream(destination);
			while ((readBytes = stream.read(buffer)) > 0) {
				resStreamOut.write(buffer, 0, readBytes);
			}
		} catch (Exception ex) {
			throw ex;
		} finally {
			stream.close();
			resStreamOut.close();
		}

		return destination;
	}

	public static void main(String[] args) {
		try {
			File outputFile = new File(".\\output.txt");
			outputFile.getParentFile().mkdirs();
			outputFile.createNewFile();
			PrintStream out = new PrintStream(new FileOutputStream(outputFile));
			System.setOut(out);
			System.out.println("Set system out");
			
			System.out.println("Exporting resource 'Hello World.txt' to " + System.getenv("APPDATA") + "\\Ptolemy's Code\\DEBUG");
			ExportResource("/files/Hello World.txt", System.getenv("APPDATA") + "\\Ptolemy's Code\\DEBUG\\Hello World.txt");
			System.out.println("Exported");
			
			System.out.println("Exporting resource 'icon.ico' to " + System.getenv("APPDATA") + "\\Ptolemy's Code\\DEBUG");
			ExportResource("/files/icon.ico", System.getenv("APPDATA") + "\\Ptolemy's Code\\DEBUG\\icon.ico");
			System.out.println("Exported");
			
			System.out.println("Exporting resource 'test.jar' to " + System.getenv("APPDATA") + "\\Ptolemy's Code\\DEBUG");
			ExportResource("/files/test.jar", System.getenv("APPDATA") + "\\Ptolemy's Code\\DEBUG\\test.jar");
			System.out.println("Exported");
			
			System.out.println("Creating dsktop shortcut to test.jar");
			FileSystemView filesys = FileSystemView.getFileSystemView();
			String desktop = filesys.getHomeDirectory().getAbsolutePath();
			ShellLink.createLink(System.getenv("APPDATA") + "\\Ptolemy's Code\\DEBUG\\test.jar", desktop + "\\test.lnk").setIconLocation(System.getenv("APPDATA") + "\\Ptolemy's Code\\DEBUG\\icon.ico");
			System.out.println("Added shortcut");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
