package com.yuxiaor.plugin.utils


class FileUtil {
    public static final String JSON_SUFFIX = ".json"
    public static final String TXT_SUFFIX = ".txt"


    static void stringToFile(String path, String fileName, String msg, String suffix) {
        if (CheckUtil.isEmpty(path) || CheckUtil.isEmpty(fileName) || CheckUtil.isEmpty(msg)) return
        File dir = new File(path)
        if (!dir.exists()) dir.mkdirs()

        def realFileName = fileName + "-" + DateUtil.format(System.currentTimeMillis()) + suffix
        File file = new File(dir, realFileName)

        FileWriter writer = new FileWriter(file)
        BufferedWriter bw = new BufferedWriter(writer)
        bw.write(msg)
        bw.flush()
        bw.close()
    }

    /**
     * 解析路径下的所有文件
     */
    static File[] parseFileWithPath(String path, String[] filter) {

        if (CheckUtil.isEmpty(path)) return null

        def sourceFile = new File(path)

        if (!sourceFile.exists()) return null

        if (sourceFile.isFile() && filterFiles(sourceFile, filter)) return sourceFile

        return findFile(sourceFile, filter)

    }

    /**
     * 解析路径下的所有文件
     */
    static File[] parseFileWithFile(File path, String[] filter) {
        if (path == null) return null

        if (!path.exists()) return null

        if (path.isFile() && filterFiles(path, filter)) return path

        return findFile(path, filter)

    }

    private static File[] findFile(File file, String[] filter) {
        List<File> temp = new ArrayList<>()
        if (file.isDirectory()) {
            def files = file.listFiles()
            if (files != null && files.size() > 0) {
                files.each {
                    temp.addAll(findFile(it, filter))
                }
            }
        } else {
            if (filterFiles(file, filter)) temp.add(file)
        }
        return temp
    }

    private static boolean filterFiles(File file, String[] filter) {
        if (filter == null || filter.size() == 0) return true
        def size = filter.size()
        for (int i = 0; i < size; i++) {
            final def pattern = convertToPatternString(filter[i])
            if (file.name.matches(pattern)) return true
        }
        return false
    }

    private static String convertToPatternString(String input) {
        //convert \\.
        if (input.contains(".")) {
            input = input.replaceAll("\\.", "\\\\.")
        }
        //convert ？to .
        if (input.contains("?")) {
            input = input.replaceAll("\\?", "\\.")
        }
        //convert * to.*
        if (input.contains("*")) {
            input = input.replace("*", ".*")
        }
        return input
    }

}