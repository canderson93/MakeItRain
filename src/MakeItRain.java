import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

public class MakeItRain {
	public static int DELAY = 50;
	
	String target;
	int[] values;

	MainWindow window;

	public MakeItRain(String target) {
		this.target = target;
		values = new int[] { 5, 10, 20, 50, 100 };

		window = new MainWindow();
	}

	public InputStream getImage(int val) {
		// randomly pick a photo
		Random random = new Random();
		int rand = random.nextInt(2);

		return this.getClass().getResourceAsStream(val + "-" + rand + ".png");
	}

	public void writeFile(InputStream in, String name)
			throws IOException {
		File file = new File(target + "/" + name + ".png");
		OutputStream out = new FileOutputStream(file);
		byte[] buf = new byte[1024];
		int read; 
		
		//Copy the files
		try {
			while ((read = in.read(buf)) > 0){
				out.write(buf, 0, read);
			}
		}  finally {
			in.close();
			out.close();
		}
	}

	private void doAmount(int val) {
		// Lets do some change making
		int total = 0;
		int num = 0;

		while (total < val) {
			int diff = val - total;

			// pick the highest val
			for (int i = values.length - 1; i > 0; i--) {
				int cur = values[i];

				if (cur <= diff) {
					try {
						writeFile(getImage(cur), Integer.toString(num));
						total += cur;
						num += 1;
						break;
					} catch (IOException e){
						e.printStackTrace();
					}
				}
				// Not possible, so stop
				else if (i == 0 && values[i] > total) {
					return;
				}
			}

			window.updateText(total);
			
			//Slow the loop a little
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void doRandom() {
		int total = 0;
		int num = 0;

		while (true) {
			Random rand = new Random(System.currentTimeMillis());
			int index = rand.nextInt(values.length);
			int cur = values[index];

			try {
				writeFile(getImage(cur), Integer.toString(num));
				total += cur;
				num += 1;
			} catch (IOException e) {
				e.printStackTrace();
			}

			window.updateText(total);
			
			//Slow the loop a little
			try {
				Thread.sleep(DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void start(int val) {
		if (val > 0) {
			doAmount(val);
		} else {
			doRandom();
		}
	}

	public static void main(String[] args) {
		MakeItRain app = new MakeItRain(".");
		
		Thread thread = new Thread(AudioLoop.createLoop("audio.wav"));
		
		thread.start();
		app.start(0);
	}

}
