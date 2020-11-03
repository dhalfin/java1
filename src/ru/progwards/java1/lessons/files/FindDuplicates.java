package ru.progwards.java1.lessons.files;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;


public class FindDuplicates {

    private List<Path> fileList(String start) {
        List<Path> fileList = new ArrayList<>();
        Path pathFile = Paths.get(start);
        PathMatcher pm = FileSystems.getDefault().getPathMatcher("glob:**/*");
        try {
            Files.walkFileTree(pathFile, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                    if (pm.matches(path)) {
                        fileList.add(path);
                        return FileVisitResult.CONTINUE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    private boolean fileChecker(Path file1, Path file2) {
        try {
            if (Files.getAttribute(file1, "lastModifiedTime")
                    .equals(Files.getAttribute(file2, "lastModifiedTime"))
                    && Files.getAttribute(file1, "size")
                    .equals(Files.getAttribute(file2, "size"))) {
                if (Arrays.equals(Files.readAllBytes(file1), Files.readAllBytes(file2))) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<List<String>> findDuplicates(String startPath) {
        List<List<String>> result = new ArrayList<>();
        List<Path> transientList = fileList(startPath);

        for (int i = 0; i < transientList.size(); i++) {
            Path firstFile = transientList.get(i);
            List<String> filePathList = new ArrayList<>();
            for (int j = i + 1; j < transientList.size(); j++) {
                Path secondFile = transientList.get(j);
                if (firstFile.getFileName().compareTo(secondFile.getFileName()) == 0
                        && fileChecker(firstFile, secondFile)) {
                    filePathList.add(firstFile.toString());
                    filePathList.add(secondFile.toString());
                    transientList.remove(j);
                }
            }
            if (filePathList.size() != 0) {
                result.add(filePathList);
            }
        }
        return result;
    }
}
