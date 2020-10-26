package practice;

public class HelloByte {
    public static void main(String[] args) {
        int a = 1;
        int b = 1;
        int c = 1;
        for(int i = 0;i < 10000;i++){
            a += i;
            b += i;
            c = a + b;
            if (c == 32){
                c = a * b;
                break;
            }
        }
        System.out.println(c);
    }
}
