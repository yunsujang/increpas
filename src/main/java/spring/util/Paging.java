package spring.util;

public class Paging {
	private int nowPage, // 현재 페이지 값
				rowTotal, // 총 게시물 수
				blockList, // 한페이지에 표현될 게시물 수
				blockPage, // 한 블럭당 표현할 페이지 수
				totalPage, // 전체 페이지 수
				startPage, //시작 페이지 값
				endPage, // 마지막 페이지 값
				begin, //한 페이지의 시작 레코드 값
				end; // 한 페이지의 마지막으로 표현할 레코드 값
	
	private boolean isPrePage; //이전 기능 가능 여부(true일 때 이전 기능 활성화)
	private boolean isNextPage; //다음 기능 가능 여부(true일 때 다음 기능 활성화)
		
	//JSP에서 표현할 페이징 HTML코드를 저장할 곳
	private StringBuffer sb;

	public Paging(int nowPage, int rowTotal, int blockList, int blockPage) {
		this.nowPage = nowPage;
		this.rowTotal = rowTotal;
		this.blockList = blockList;
		this.blockPage = blockPage;
		
		//이전 기능과 다음기능을 초기화 시킨다.
		isPrePage = false;
		isNextPage = false;
		
		//입력된 전체 게시물의 수를 구해 전체 페이지 값을 구한다.
		totalPage = (int)Math.ceil((double)rowTotal/blockList);
		
		//현재 페이지 값이 전체 페이지 값보다 크다면 전체페이지 값을 현재페이지 값으로 지정
		if(nowPage > totalPage)
			nowPage = totalPage;
		
		//현재 블럭의 시작 페이지값과 마지막 페이지 값을 구하자!
		startPage = (int)((nowPage-1)/blockPage)*blockPage+1;
		endPage = startPage + blockPage -1;
		 
		//마지막 페이지의 값이 전체 페이지의 값보다 크다면
		//마지막 페이지 값을 전체페이지 값으로 지정
		if(endPage>totalPage)
			endPage = totalPage;
		
		//현재 페이지 값에 의해 시작 게시물의 행번호와 
		//마지막 게시물의 행번호를 지정하여 현재 페이지에 보여질 게시물 목록을 얻을 준비를 하자!
		begin = (nowPage-1)*blockList+1;
		end = nowPage * blockList;
		
		//이전 기능 활성화 여부 확인
		if(startPage > 1)
			isPrePage = true;
		
		//다음 기능 활성화 여부 확인
		if(endPage < totalPage)
			isNextPage = true;
		
		//이제 현재 페이지 값, 시작페이지 값과 마지막 페이지값을 알았으니
		//페이지 기법에 사용할 코드를 작성하여 StringBuffer에 저장하자!
		sb = new StringBuffer("<ol class='paging'>");
		
		if(isPrePage) {
			sb.append("<li> <a href='list.inc?cPage=");
			sb.append(nowPage - blockPage);
			sb.append("'> &lt; </a></li>");
		}else
			sb.append("<li class = 'disable'>&lt;</li>");
		
		//페이지 번호를 출력하는 반복문
		for(int i = startPage; i<= endPage; i++) {
			if(nowPage == i) {
				sb.append("<li class='now'>");
				sb.append(i);
				sb.append("</li>");
			}
			
			else {
				sb.append("<li><a href='list.inc?cPage=");
				sb.append(i);
				sb.append("'>");
				sb.append(i);
				sb.append("</a></li>");
			}
		}
			//다음 기능 여부 확인
			if(isNextPage) {
				sb.append("<li> <a href='list.inc?cPage=");
				sb.append(nowPage + blockPage);
				sb.append("'> &gt; </a></li>");
			}
			else
				sb.append("<li class='disable'>&gt; </li");
		
		
		sb.append("</ol>");
	}
	
	public int getNowPage() {
		return nowPage;
	}

	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	public int getRowTotal() {
		return rowTotal;
	}

	public void setRowTotal(int rowTotal) {
		this.rowTotal = rowTotal;
	}

	public int getBlockList() {
		return blockList;
	}

	public void setBlockList(int blockList) {
		this.blockList = blockList;
	}

	public int getBlockPage() {
		return blockPage;
	}

	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public boolean isPrePage() {
		return isPrePage;
	}

	public void setPrePage(boolean isPrePage) {
		this.isPrePage = isPrePage;
	}

	public boolean isNextPage() {
		return isNextPage;
	}

	public void setNextPage(boolean isNextPage) {
		this.isNextPage = isNextPage;
	}

	public StringBuffer getSb() {
		return sb;
	}

	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}
	
	
	
}	
