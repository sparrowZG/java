import java.util.concurrent.ThreadPoolExecutor;

public class DemoThreadLocal {
	private static String noCom;
	private static ThreadLocal<String> com = new ThreadLocal<>();
	public static void main(String[] args) {
		noCom = "mainCom";
		com.set("main");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				noCom = "ThreadLocal-noCom";
				com.set("ThreadLocal-com");
			}
		});
		thread.start();//启动线程修改变量
		try {
			thread.join();//等待线程结束
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//com.remove();//每次使用完,后记得要释放,防止内存泄漏,
		//
		System.out.println(noCom);//ThreadLocal-noCom
		System.out.println(com.get());//main
	}
}
