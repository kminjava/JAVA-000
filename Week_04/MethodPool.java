package java0.conc0303.homework;



import java.util.concurrent.*;

public class MethodPool {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        ExecutorService executor = Executors.newFixedThreadPool(1);
        FutureTask<Integer> futureTask = new FutureTask<>(()-> sum());

        executor.submit(futureTask);
        Integer result = futureTask.get();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }


    private static int sum() {

        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}



