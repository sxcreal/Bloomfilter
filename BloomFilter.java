import java.util.BitSet;

public class BloomFilter 
{
	
private static final int DEFAULT_SIZE =1<<25; /* BitSet初始分配2^24个bit */ 
private static final int[] seeds =new int[] { 5, 7, 11, 13, 31, 37, 61 };/* 不同哈希函数的种子，一般应取质数 */
private BitSet bits =new BitSet(DEFAULT_SIZE);/* 哈希函数对象 */ 
private SimpleHash[] func =new SimpleHash[seeds.length];

public BloomFilter() 
{
	for (int i =0; i < seeds.length; i++)
	{
		func[i] =new SimpleHash(DEFAULT_SIZE, seeds[i]);
	}
}
public void add(String value) // 将字符串标记到bits中
{
	for (SimpleHash f : func) 
	{
		bits.set(f.hash(value), true);
	}
}
public boolean contains(String value) //判断字符串是否已经被bits标记
{
	if (value ==null) 
	{
		return false;
	}
	boolean ret = true;
	for (SimpleHash f : func) 
	{
		ret = ret && bits.get(f.hash(value));
	}
	return ret;
}
public static class SimpleHash /* 哈希函数类 */
{
	private int cap;
	private int seed;
	public SimpleHash(int cap, int seed) 
	{
	this.cap = cap;
	this.seed = seed;
	}

	public int hash(String value) //hash函数，采用简单的加权和hash
	{
		int result =0;
		int len = value.length();
		for (int i =0; i < len; i++) 
			{
				result = seed * result + value.charAt(i);
			}
		return (cap -1) & result;
		}
	}
}