public class Main {
    public static void main(String[] args) {
        String str = HttpClient.doGet("http://localhost:8801");
        System.out.println(str);
    }
}
