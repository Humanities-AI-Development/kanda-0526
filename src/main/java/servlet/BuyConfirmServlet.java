package servlet;
import java.io.IOException;
import java.util.ArrayList;

import bean.Book;
import bean.Order;
import bean.User;
import dao.BookDAO;
import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//// 注文登録・メール送信用サーブレット
@WebServlet("/buyConfirm")
public class BuyConfirmServlet extends HttpServlet {//カート追加用のサーブレット
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
//		①セッションから"user"を取得する。(セッション切れの場合はerror.jspに遷移する)
//		②セッションから"order_list"を取得する。(カートの中身がない場合はerror.jspへ遷移する)
//		③②BookDAOとOrderDAOをインスタンス化し、関連メソッドをorder_listの(カート追加データ分）だけ
//		呼び出す。
//		④③中に取得した各BookをListに追加し、リクエストスコープに"book_list"という名前で格納する。
//		⑤"order_list"の注文情報内容をメール送信する。
//		⑥セッションの"order_list"情報をクリアする。
//		⑦buyConfirm.jspにフォワードする。
		
		String error = "";
		String link = "";
		try {
			
//			①セッションから"user"のUserオブジェクトを取得する(セッション切れの場合はエラー)。
			//*userセッションはLoginServletで登録済み
			HttpSession session = request.getSession();			// HttpServletRequestオブジェクトからセッションを取得
			User user = (User)session.getAttribute("user");//セッションから、user属性を取得
			if(user==null) {
				error="セッション切れです。";
				request.setAttribute("error", error);
				request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			}

//			②セッションから"order_list"を取得する。(カートの中身がない場合はerror.jspへ遷移する)
//			*order_list:InsertIntoCartServletで定義したカート内一覧が格納された配列
			ArrayList<Order> order_list = (ArrayList<Order>)session.getAttribute("order_list");

//			③②BookDAOとOrderDAOをインスタンス化し、関連メソッドをorder_listの(カート追加データ分）だけ
//			呼び出す。
			
			Book book = new Book();//データ格納のためにフィールド準備
			BookDAO BookobjDao = new BookDAO();//DB関連のメソッドを使うためにインスタンス化
			OrderDAO OrderobjDao = new OrderDAO();
			
			for(Order order:order_list) {
				OrderobjDao.insert(order);//Orderテーブルにインサート
				BookobjDao.selectByIsbn(order.getIsbn());//isbnと等しいデータがbookに格納される
			}
			
//			④③中に取得した各BookをListに追加し、リクエストスコープに"book_list"という名前で格納する。
			request.setAttribute("book_list",book);
			
//			⑤"order_list"の注文情報内容をメール送信する。

			
		}catch(IllegalStateException e) {
			error = "DB接続エラーの為、書籍詳細は表示できませんでした。";
			link = request.getContextPath() + "/view/menu.jsp";  
            
            request.setAttribute("error", error);
            request.setAttribute("link", link);
            
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
			 
		}catch(Exception e) {
			error = "予期せぬエラーが発生しました。<br>"+e;
			link = request.getContextPath() + "/view/menu.jsp";  
            
            request.setAttribute("error", error);
            request.setAttribute("link", link);
            
			request.getRequestDispatcher("/view/error.jsp").forward(request, response);
		}
	}
}