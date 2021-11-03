import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tag {
    private byte position;
    private char nextChar;

    public Tag(byte position, char nextChar) {
        this.position = position;
        this.nextChar = nextChar;
    }

    public Tag(byte[] tagBytes) {
        position = tagBytes[0];
        nextChar = (char) tagBytes[1];
    }

    public byte[] toByteArray() {
        byte[] bytes = new byte[2];

        bytes[0] = position;
        bytes[1] = (byte) nextChar;

        return bytes;
    }

    public static byte[] tagsToByteArray(List<Tag> tags){
        byte[] result =  new byte[tags.size() * 2];

        int tagsCounter = 0, resultCounter = 0;
        while (tagsCounter < tags.size()){
            byte[] tagByte = tags.get(tagsCounter).toByteArray();
            result[resultCounter] = tagByte[0];
            result[resultCounter + 1] = tagByte[1];
            resultCounter += 2;
            tagsCounter++;
        }

        return result;
    }

    public static List<Tag> bytesToTags(byte[] bytes) {
        List<Tag> tags = new ArrayList<>(bytes.length / 2);

        for (int i = 0; i < bytes.length / 2; i++) {
            tags.add(new Tag(Arrays.copyOfRange(bytes, i*2, (i*2 + 1))));
        }

        return tags;
    }

    public byte getPosition() {
        return position;
    }

    public void setPosition(byte position) {
        this.position = position;
    }

    public char getNextChar() {
        return nextChar;
    }

    public void setNextChar(char nextChar) {
        this.nextChar = nextChar;
    }
}
