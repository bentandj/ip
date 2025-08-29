import java.io.*;

public class Pecky {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("./src/main/java/level0output"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String level0output = sb.toString();
            System.out.println(level0output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            br.close();
        }
    }
}
