package test;

/**
 * author：yuxinfeng on 2019-04-18 13:59
 * email：yuxinfeng@corp.netease.com
 */
public class TestInercept {

    public static void main(String[] args) {
        // 初始化一个线程
        Thread thread = new Thread("中断") {
            public void run() {
                try {
                    /**
                     * 注意：sleep方法揭示了java线程何时、如何响应中断
                     * public static native void sleep(long millis) throws InterruptedException;
                     */
                    Thread.sleep(600 * 1000);
                } catch (InterruptedException e) {
                    /**
                     * 结果为true，因此当前线程thread中断状态标志位已经置位
                     */
                    Thread.currentThread().interrupt();
                    if (Thread.interrupted())
                        System.out.println("当前线程状态：中断");
                    e.printStackTrace();
                }
            }
        };
        /**
         * 当“中断”线程启动以后，此时系统中存在两个线程
         * 1. main线程；
         * 2. “中断”线程；
         */
        thread.start();
        // 为了通过工具分析线程状态，这里将主线程暂停一分钟，方便观察
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * 在主线程中中断（告诫）thread，此时thread修改了中断标志位，但是并没有立即响应
         */
        thread.interrupt();
        /**
         * 输出false，因为主线程此时并没有被中断
         */
        System.out.println(Thread.currentThread().isInterrupted());
    }

}
