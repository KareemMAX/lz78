import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class LZ78 {
    public static void main(String[] args) {
        try
        {
            if (args.length == 2) {
                File file = new File(args[1]);
                if (file.exists()) {
                    if (args[0].toLowerCase().contains("c")) { // Compress
                        String input = Files.readString(file.toPath());

                        List<Tag> compressedTags = compress(input);
                        for (Tag tag :
                                compressedTags) {
                            System.out.println("Tag<" + tag.getPosition() + ", " + tag.getNextChar() + ">");
                        }

                        byte[] compressedBytes = Tag.tagsToByteArray(compressedTags);

                        String newPath = file.getPath() + ".lz78";
                        File compressedFile = new File(newPath);
                        compressedFile.createNewFile();
                        Files.write(compressedFile.toPath(), compressedBytes);
                    } else if (args[0].toLowerCase().contains("d")) { // Decompress
                        byte[] input = Files.readAllBytes(file.toPath());

                        String decompressedTxt = decompress(Tag.bytesToTags(input));
                        System.out.println(decompressedTxt);

                        String newPath = file.getPath() + ".txt";

                        File decompressedFile = new File(newPath);
                        decompressedFile.createNewFile();

                        List<String> lines = new ArrayList<>();
                        lines.add(decompressedTxt);
                        Files.write(decompressedFile.toPath(), lines);
                    } else {
                        System.out.println(args[0] + " is invalid argument");
                    }
                } else {
                    System.out.println(args[1] + " is not an existing file");
                }
            } else {
                System.out.println("No arguments were supplied");
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Tag> compress(String str){
        ArrayList<String> dictionary = new ArrayList<>();
        List<Tag> tags = new ArrayList<>();
        dictionary.add("\0");

        for (int i = 0; i < str.length();){
            // Reset the dictionary
            if (dictionary.size() == 256) {
                dictionary.clear();
                dictionary.add("\0");
            }

            int dictionaryIndex = 0;
            StringBuilder temp_str = new StringBuilder();
            for (int j = i; j < str.length(); j++){
                temp_str.append(str.charAt(j));
                if (dictionary.contains(temp_str.toString())) {
                    dictionaryIndex = dictionary.indexOf(temp_str.toString());
                    if (j == str.length() - 1) {
                        Tag tag = new Tag((byte) dictionaryIndex, '\0');
                        tags.add(tag);
                        i += temp_str.length();
                    }
                }
                else {
                    char next = temp_str.charAt(temp_str.length() - 1);
                    dictionary.add(temp_str.toString());

                    Tag tag = new Tag((byte) dictionaryIndex, next);
                    tags.add(tag);
                    i += temp_str.length();
                    break;
                }
            }
        }

        return tags;
    }

    private static String decompress(List<Tag> tags) {
        ArrayList<String> dictionary = new ArrayList<>();
        StringBuilder decompressed = new StringBuilder();

        for (Tag tag :
                tags) {
            // Reset the dictionary
            if (dictionary.size() == 255) {
                dictionary.clear();
            }

            if(tag.getPosition() == 0) {
                dictionary.add(String.valueOf(tag.getNextChar()));
                if(tag.getNextChar() != 0)
                    decompressed.append(tag.getNextChar());
            }
            else {
                StringBuilder tagBuilder = new StringBuilder();
                tagBuilder.append(dictionary.get(tag.getPosition() - 1));
                if(tag.getNextChar() != 0)
                    tagBuilder.append(tag.getNextChar());

                dictionary.add(tagBuilder.toString());
                decompressed.append(tagBuilder);
            }
        }

        return decompressed.toString();
    }
}
