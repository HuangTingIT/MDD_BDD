package ��;
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

              InputStreamReader read = new InputStreamReader(new FileInputStream(file)); //���ǵ������f

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
    	 //�����Ǵ���JFileChooser �������������������ʾĬ�ϴ򿪵�Ŀ¼��������Ĭ�ϴ򿪵�ǰ�ļ����ڵ�Ŀ¼��
    	   JFileChooser file = new JFileChooser (".");
    	   //���������ȥ����ʾ�����ļ������������
    	   file.setAcceptAllFileFilterUsed(false);

    	   /*ʹ��showOpenDialog()��������ʾ����ѡ���ļ��Ĵ��ڣ���ѡ����ĳ���ļ��󣬻��߹رմ˴�����ô���᷵��һ��
    	   ������ֵ��������ص���0�������Ѿ�ѡ����ĳ���ļ����������1����ѡ����ȡ����ť����ֱ�ӹر��˴���*/
    	   int result = file.showOpenDialog(null);
    	   //JFileChooser.APPROVE_OPTION�Ǹ����ͳ���������0������˵������0��ֵ���ǲ�ִ����ز���������ʲôҲ������
    	   if(result == JFileChooser.APPROVE_OPTION)
    	   {
    	   //�����ѡ����ļ�����·�������������Ȼ�����ǻ�����·���󻹿������ܶ���¡�
    	      String path = file.getSelectedFile().getAbsolutePath();
    	      return path;
    	   }
    	   else
    	   {
    	       System.out.println("����ȡ�����ر��˴��ڣ�");
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
