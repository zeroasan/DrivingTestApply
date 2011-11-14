package zeroasan;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class JsoupTest {

	public static void main(String[] args) throws Exception {
		File file1 = new File("");
		System.out.println(file1.getAbsolutePath());
		File file = new File("src/test/resource/预约信息.htm");
//		FileReader reader = new FileReader(file);
		Reader reader = new InputStreamReader(new FileInputStream(file), "UTF-8");
		
		int readLength = 1024;
		boolean flag = false;
		
		char[] cbuf = new char[1024*8];
		
		int totalLength = 0;
		while(!flag) {
			int count = reader.read(cbuf, totalLength, readLength);
			if(count < readLength) {
				flag = true;
			}
			totalLength += count;
		}
		
		String html = String.valueOf(cbuf, 0, totalLength);
		System.out.println(html);
		
//		Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("form[name=form1] span[id=Label1]");
		System.out.println(elements.get(0).text());
		System.out.println(getAnswer(elements.get(0).text()));
	}
	
	
	static String getAnswer(String question) {
		String idCard = "123456789012345678";
		if(question.contains("姓名")) {
			return "姓名";
		} else if(question.contains("身份证")) {
			//抽取question中的数字？有没有可能是中文汉字？？
			int length = 0;
			for(int i = 18; i > 0; i-- ) {
				if(question.contains(String.valueOf(i)))
					length = i;
			}
			
			if(question.contains("前")) {
				return idCard.substring(0, length);
			} else if(question.contains("后")) {
				return idCard.substring(idCard.length() - length, idCard.length());
			} else {
				return "没找到答案";
			}
		} else {
			return "没找到答案";
		}
	}
}
