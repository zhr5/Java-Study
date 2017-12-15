import java.util.*;

/**
 * 统计出现次数最多的单词和它出现的次数
 * @author ZHR
 */
public class CountWord {
	public static String [] strTostrArray(String str) {
		/*
		 * 将非字母字符全部替换为空格字符" "
		 * 得到一个全小写的纯字母字符串包含有空格字符
		 */
		str=str.toLowerCase();//将字符串中的英文部分的字符全部变为小写
		String regex="[\\W]+";//非字母的正则表达式 --\W：表示任意一个非单词字符
		str=str.replaceAll(regex, " ");
		String[] strs=str.split(" "); //以空格作为分隔符获得字符串数组
		return strs;
	}
	public static void countword(String [] strs) {
		/*
		 * 建立字符串(String)出现次数(Integer)的映射
		 */
		HashMap<String, Integer> strhash = new HashMap<String, Integer>();
		Integer in=null;//用于存放put操作的返回值
		for(String s:strs){//遍历数组 strs
			in=strhash.put(s, 1);
			if(in!=null){//判断如果返回的不是null，则+1再放进去就是出现的次数
				strhash.put(s, in+1);
			}
		}
		Set<java.util.Map.Entry<String, Integer>> entrySet=strhash.entrySet();
		String maxStr=null;//用于存放出现最多的单词
		int maxValue=0;//用于存放出现最多的次数				
		for(java.util.Map.Entry<String, Integer> e:entrySet){
			String key=e.getKey();
			Integer value=e.getValue();
			if(value>maxValue){
				maxValue=value;//这里有自动拆装箱
				maxStr=key;
			}
		}
		System.out.println("出现最多的单词是："+maxStr+"出现了"+maxValue+"次");
     }
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		System.out.println("请输入一行英文句子：");
		String str=scan.nextLine();
		System.out.println("输入的英文句子为："+str);
		String [] strs=strTostrArray(str);
	    countword(strs);
   }
}
