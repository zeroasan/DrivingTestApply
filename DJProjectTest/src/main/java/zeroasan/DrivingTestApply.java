package zeroasan;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import chrriis.common.UIUtils;
import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;

/**
 * @author Zeroasan
 */
public class DrivingTestApply extends JPanel {

	protected static final String LS = System.getProperty("line.separator");

//	private static final String SITE_LOGIN = "http://www.csdn.net/";
	private static final String SITE_LOGIN = "http://203.91.44.33/Login.aspx";
//	private static final String SITE_MAIN_PAGE = "http://www.programmer.com.cn/";
	private static final String SITE_MAIN_PAGE = "http://203.91.44.33/frame1.htm";
	
	private static final String APPLY_PAGE = "http://203.91.44.33/Exam2_Sanxue.aspx";

	//用户信息：用户名、密码、姓名、身份证号
	//提示信息：姓名、身份证前后N位：分析出姓名，身份证，如果为身份证则判断前后及抽取数字
	private User user;
	
	public DrivingTestApply(User user) {
		super(new BorderLayout());
		
		this.user = user;
		
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		final JWebBrowser webBrowser = new JWebBrowser();
		webBrowser.setBarsVisible(false);
		webBrowser.setStatusBarVisible(true);
		webBrowser.navigate(SITE_LOGIN);
 
		webBrowser.addWebBrowserListener(new WebBrowserAdapter() {

			@Override
			public void locationChanging(WebBrowserNavigationEvent e) {
				final String newResourceLocation = e.getNewResourceLocation();
				if (newResourceLocation.startsWith(SITE_MAIN_PAGE)) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
//							webBrowser.executeJavascript("alert('Invoke this success!')");
						}
					});
				}
			}
			
			//TODO 防止连续提交
			@Override
			public void loadingProgressChanged(WebBrowserEvent e) {
				String resourceLocation = webBrowser.getResourceLocation();
				if(webBrowser.getLoadingProgress() == 100 && resourceLocation.startsWith(SITE_MAIN_PAGE)) {
					webBrowser.executeJavascript("window.location='" + APPLY_PAGE + "'");
				} else if(webBrowser.getLoadingProgress() == 100 && resourceLocation.startsWith(APPLY_PAGE)) {
					Document doc = Jsoup.parse(webBrowser.getHTMLContent());
					Elements txtNum = doc.select("form[name=form1] input[id=txtNum]");
					if(txtNum.size() == 0) { //未点问题前
//						webBrowser.executeJavascript("if(!document.getElementById('txtNum')){__doPostBack('LinkButton1','')} else {document.getElementById('txtNum').value = '"+ answer +"';alert('接下来触发【确定预约】按钮');}");
						webBrowser.executeJavascript("__doPostBack('LinkButton1','');");
					} else {
						Elements elements = doc.select("form[name=form1] span[id=Label1]");
						String answer = getAnswer(elements.get(0).text());
						webBrowser.executeJavascript("document.getElementById('txtNum').value = '"+ answer +"';");
						webBrowser.executeJavascript("alert('接下来触发【确定预约】按钮');");
						
					}
				}
			}

			//让页面始终保持在同一windowBrowser内。
			@Override
			public void windowWillOpen(WebBrowserWindowWillOpenEvent e) {
				// We let the window to be created, but we will check the first
				// location that is set on it.
				e.getNewWebBrowser().addWebBrowserListener(new WebBrowserAdapter() {
					@Override
					public void locationChanging(WebBrowserNavigationEvent e) {
						final JWebBrowser webBrowser1 = e.getWebBrowser();
						webBrowser1.removeWebBrowserListener(this);
						webBrowser1.setJavascriptEnabled(false);
						String newResourceLocation = e.getNewResourceLocation();
						
						webBrowser.navigate(newResourceLocation);
						
						e.consume();
							// The URL Changing event is special: it is
							// synchronous so disposal must be deferred.
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								webBrowser1.getWebBrowserWindow().dispose();
							}
						});
					}
				});
			}
		});
			
		tabbedPane.addTab("Controled Browser", webBrowser);
		add(tabbedPane, BorderLayout.CENTER);
	}

	/* Standard main method to try that test as a standalone application. */
	public static void main(String[] args) {
		final User user = new User();
		user.idCard = "440306198803031322";
		user.userId = user.idCard;
		user.password = "0";
		user.name = "XXX";
		
		UIUtils.setPreferredLookAndFeel();
		NativeInterface.open();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrame frame = new JFrame("DJ Native Swing Test");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().add(new DrivingTestApply(user), BorderLayout.CENTER);
				frame.setSize(800, 600);
				frame.setLocationByPlatform(true);
				frame.setVisible(true);
			}
		});
		NativeInterface.runEventPump();
	}
	
	
	private String getAnswer(String question) {
		if(question.contains("姓名")) {
			return this.user.name;
		} else if(question.contains("身份证")) {
			//抽取question中的数字？有没有可能是中文汉字？？
			int length = 0;
			for(int i = 18; i > 0; i-- ) {
				if(question.contains(String.valueOf(i)))
					length = i;
			}
			
			if(question.contains("前")) {
				return this.user.idCard.substring(0, length);
			} else if(question.contains("后")) {
				return this.user.idCard.substring(this.user.idCard.length() - length, this.user.idCard.length());
			} else {
				return "没找到答案";
			}
		} else {
			return "没找到答案";
		}
	}

}
