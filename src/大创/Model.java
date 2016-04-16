package 大创;
import java.io.*;



import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class Model 
{
       Relation rs[]=null;
       int rnum=0;
       
       Domain   ds[]=null;
       int dnum=0;
       
       Variable vs[]=null;
       int vnum=0;
       
       Constraint cs[]=null;
       int cnum=0;
       
       int maxd=0;
       
       int maxr=0;
       
       public static int deln=0;
       public static int all=0;
       
       
       Relation rcur[]=null;
       int rnumcur=0;
       Nodebox inrcur=new Nodebox();
       
       Nodebox sol=new Nodebox();
       int  soln=0;
       public Model(int a, String name)
       {
    	   try
    	   {
      		  File file = new File(name); 

              InputStreamReader read = new InputStreamReader(new FileInputStream(file)); //考虑到编码格f

              BufferedReader bufferedReader = new BufferedReader(read);

              String line = null;

              line = bufferedReader.readLine();

              dnum=Integer.parseInt(line);

              ds=new Domain[dnum];
              
              for(int i=0;i<dnum;i++)
              {
                  line = bufferedReader.readLine();
                  
                  String as[]=line.split(" ");
                  
                  ds[i]=new Domain(as); 
                  
                  if(maxd<ds[i].num)maxd=ds[i].num;
              }
              
              line = bufferedReader.readLine();
     		  vnum=Integer.parseInt(line);;
     		  vs=new Variable[vnum];
             // System.out.println(vnum);
              
              for(int i=0;i<vnum;i++)
     		  {
                  line = bufferedReader.readLine();
                  
                  String as[]=line.split(" ");
                  
            	  int d=Integer.parseInt(as[1]);
            	  vs[i]=new Variable(ds[d],i,cnum);
     		  }
     		  
     		  
              line = bufferedReader.readLine();
     		  rnum=Integer.parseInt(line);;
     		  rs=new Relation[rnum];
     		  //System.out.println(rnum);
     		  for(int i=0;i<rnum;i++)
     		  {
                  line = bufferedReader.readLine();
                  
                  String as[]=line.split(" ");


         		  int invnum=Integer.parseInt(as[2]);
         		  int inrnum=Integer.parseInt(as[4+invnum]);
  
         		  rs[i]=new Relation(inrnum,invnum,as, Integer.parseInt(as[0]),ds);     
     		  }
     		  
     		  
              line = bufferedReader.readLine();
     		  cnum=Integer.parseInt(line);;   
     		  cs=new Constraint[cnum];  

     		  for(int i=0;i<vnum;i++)
     		  {
     			  vs[i].cs=new Constraint[cnum];
     		  }
     		  
     		  for(int i=0;i<cnum;i++)
     		  {
                  line = bufferedReader.readLine();
                  
                  String as[]=line.split(" ");
                  
     			  int invnum=Integer.parseInt(as[0]);
     			  int inr=Integer.parseInt(as[1+invnum]);

     			  Variable invs[]=new Variable[invnum];
     			  
     			  int n=1;
     			  for(int j=0;j<invnum;j++)
     			  {
     				  invs[j]=vs[Integer.parseInt(as[n])];
     				//System.out.print(" "+as[n]);
     				  n++;
     			  }
     			  
     			  //System.out.println(" "+i+" "+inr+" ");
     			  
     			  cs[i]=new Constraint(invnum,rs[inr],invs,i,this);
     			  
         		  if(cs[i].rnum>maxr)
         		  {
         			  maxr=cs[i].rnum;
         		  }
     		  }
     		  
              read.close();
      		  
    	   }
    	   catch(Exception e)
    	   {
               e.printStackTrace();
    	   }
       }
             
       
       static String  readfilename()
       {
    	 //首先是创建JFileChooser 对象，里面带个参数，表示默认打开的目录，这里是默认打开当前文件所在的目录。
    	   JFileChooser file = new JFileChooser (".");
    	   //下面这句是去掉显示所有文件这个过滤器。
    	   file.setAcceptAllFileFilterUsed(false);

    	   /*使用showOpenDialog()方法，显示出打开选择文件的窗口，当选择了某个文件后，或者关闭此窗口那么都会返回一个
    	   整型数值，如果返回的是0，代表已经选择了某个文件。如果返回1代表选择了取消按钮或者直接关闭了窗口*/
    	   int result = file.showOpenDialog(null);
    	   //JFileChooser.APPROVE_OPTION是个整型常量，代表0。就是说当返回0的值我们才执行相关操作，否则什么也不做。
    	   if(result == JFileChooser.APPROVE_OPTION)
    	   {
    	   //获得你选择的文件绝对路径。并输出。当然，我们获得这个路径后还可以做很多的事。
    	      String path = file.getSelectedFile().getAbsolutePath();
    	      return path;
    	   }
    	   else
    	   {
    	       System.out.println("你已取消并关闭了窗口！");
    	       return null;
    	   }    	 
       }
       
       
       public static void main(String[] args)
       {
   
    	try
    	{   
  
     	    String ssname=readfilename();

    		File  f=new File("solution.txt");
   	     	f.createNewFile();
   	     	FileOutputStream fos = new FileOutputStream(f);
   	     	PrintWriter pw = new PrintWriter(fos);

   	     	String name=ssname;
  		 
   	     	Model m=new Model(0,name);  
   	     	
			DATA data=null;
			
    	     try
    	     { 	  
    	    	 			data=new DATA(m);
    	    	 
    	              		FSTR2 str1=new FSTR2(data,m);

    	              		str1.MAC();   	           
    	              		
    	              		System.out.println(m.soln);
    	              		
    		   	      	    str1=null;
    		   	      	    data=null;
 		   	 }
    	     catch(Exception e)
          	 {
				     pw.close();
				     fos.close();
          			 e.printStackTrace();
          	 }	
  
             pw.write(""+m.soln);
             pw.write("\r\n");
             
             pw.flush();   		   
 
             ////////////////////////////////////////////////////
        	 String ss="";
             for(int i=0;i<m.vnum-1;i++)
             {
            	 ss+=m.vs[i].d.num+" ";
             }
             ss+=m.vs[m.vnum-1].d.num;
             pw.write(""+ss);
             pw.write("\r\n"); 
             ///////////////////////////////////////////////////

             m.sol.gettuple(m.vnum, m.soln, pw);
             ///////////////////////////////////////////////////
             pw.close();
             fos.close();	 	
             /////////////////////////////////////////////////////////////////      
 			//Selection gui=new Selection();
             //gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            MDD mdd=new MDD();
             //mdd.MDDCreate();
             BDD bdd=new BDD();
             //bdd.BDDCreate();

    		
    		
   }
   catch(Exception e)
   {
	   e.printStackTrace();
   }
  }
       
       
      

		   
	  

    
       
}
