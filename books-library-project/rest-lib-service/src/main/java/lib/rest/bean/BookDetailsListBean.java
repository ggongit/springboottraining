package lib.rest.bean;

import java.util.ArrayList;
import java.util.List;

import lib.grpc.services.auto.BookLibraryProtos.BookDetails;

public class BookDetailsListBean {
	
	private List<BookDetailsBean> bookDetailBeanList;
	public BookDetailsListBean(List<BookDetails> grpcBookDetailsList)
	{
		bookDetailBeanList = new ArrayList<BookDetailsBean>();
		grpcBookDetailsList.forEach(grpcBook->{
			
			BookDetailsBean bean = new BookDetailsBean();
			bean.setIsbn(grpcBook.getIsbn());
			bean.setTitle(grpcBook.getTitle());
			bean.setAuthor(grpcBook.getTitle());
			bean.setPublisher(grpcBook.getPublisher());
			bookDetailBeanList.add(bean);
		});
	}
	
	public List<BookDetailsBean> getBookDetailBeanList() {
		return bookDetailBeanList;
	}
	
	public class BookDetailsBean
	{
		private Integer id;
		private Long isbn;
		private String title;
		private String author;
		private String publisher;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Long getIsbn() {
			return isbn;
		}
		public void setIsbn(Long isbn) {
			this.isbn = isbn;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getPublisher() {
			return publisher;
		}
		public void setPublisher(String publisher) {
			this.publisher = publisher;
		}
	}

}
