package com.sundayliu.android.listview;

public class BookShelfListView {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置主页面的标题栏
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_bar);
        
        //更新标题栏中按钮的字体大小
        Button btnManage = (Button) findViewById(R.id.btn_manage);
        btnManage.setTextSize(10);
        
        //获取书架列表数据
        ArrayList<BookData> bookshelf = new ArrayList<BookData>();
        BookData bookData = new BookData();
        bookData.setAuthor("天蚕土豆");
        bookData.setCurrentNum(1);
        bookData.setDescription("修炼一途，乃窃阴阳，夺造化，转涅盘，握生死，掌轮回。武之极，破苍穹，动乾坤！");
        bookData.setId(1);
        bookData.setImageUrl("http://www.easou.org/files/article/image/0/308/308s.jpg");
        bookData.setLastTitle("第一千两百九十四章 魔皇之手");
        bookData.setName("武动乾坤");
        bookData.setTotalNum(1294);
        
        BookData bookData2 = new BookData();
        bookData2.setAuthor("忘语");
        bookData2.setCurrentNum(2343);
        bookData2.setDescription("一个普通的山村穷小子，偶然之下，进入到当地的江湖小门派，成了一名记名弟子。他以这样的身份，如何在门派中立足？又如何以平庸的资质，进入到修仙者的行列？和其他巨枭魔头，仙宗仙师并列于山海内外？希望书友们喜欢本书！");
        bookData2.setId(2342);
        bookData2.setImageUrl("http://www.easou.org/files/article/image/0/289/289s.jpg");
        bookData2.setLastTitle("第十一卷 真仙降世 第两千三百四十三章 九目血蟾");
        bookData2.setName("凡人修仙传");
        bookData2.setTotalNum(2343);
        
        bookshelf.add(bookData);
        bookshelf.add(bookData2);
        bookshelf.add(bookData);
        bookshelf.add(bookData2);
        bookshelf.add(bookData);
        bookshelf.add(bookData2);
        
        bookshelfListViewAdapter bookshelfListViewAdapter = new bookshelfListViewAdapter(bookshelf, this);
        ListView listView = (ListView) findViewById(R.id.listview_bookshelf);
        listView.setAdapter(bookshelfListViewAdapter);
    }
}
