package com.wow.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class App {

  public static void main(String[] args) throws IOException {
    Scanner s1 = new Scanner(System.in);
    System.out.println("Enter parent folder path:");
    String parentFolderPath;
    parentFolderPath = s1.nextLine();
    File parentFolder = new File(parentFolderPath);
    if (!parentFolder.exists()) {
      System.err.println("Directory doesn't exist!");
      return;
    }

    System.out.println("Enter file name(s) to search:");
    String filenameToSearch = s1.nextLine();
    String outputDirPath = System.getenv("UserProfile") + "/Desktop/" + filenameToSearch;
    System.out.println("Files, if found, will be copied to " + outputDirPath);
    File outputDir = new File(outputDirPath);
    // Creating the directory
    boolean dirCreated = outputDir.mkdir();
    if (dirCreated) {
      getAllFilesContainingName(parentFolder, filenameToSearch, outputDir);
    } else {
      System.out.println(
          "Output directory already exists. Please delete it and run this utility again");
    }
  }

  public static void getAllFilesContainingName(File directory, String fileToSearch, File outputDir)
      throws IOException {
    // get all the files from a directory
    File[] fList = directory.listFiles();

    if (null != fList) {
      for (File file : fList) {
        if (file.isFile() && file.getName().toLowerCase().contains(fileToSearch.toLowerCase())) {
          System.out.println(file.getAbsolutePath());
          Files.copy(
              file.toPath(),
              new File(outputDir, file.getParentFile().getName() + "-" + file.getName()).toPath(),
              StandardCopyOption.REPLACE_EXISTING);
        } else if (file.isDirectory()) {
          getAllFilesContainingName(file, fileToSearch, outputDir);
        }
      }
    }
  }
}
