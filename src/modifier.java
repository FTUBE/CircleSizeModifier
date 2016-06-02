import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class modifier {
	public static void main(String[] args) throws IOException{
		String relPath = "/Users/tianyang/Desktop/Songs/";
		File file=new File(relPath);
		File[] tempList = file.listFiles();
		for(File f : tempList){
			if(!f.isDirectory()) continue;
			String relPath2 = f.getName();
			System.out.print("\n"+relPath2);
			String absPath = relPath + relPath2 + "/";
			File innerf=new File(absPath);
			File[] innerfiles = innerf.listFiles();
			for(File inf : innerfiles){
				if(isOsu(inf.getName())){
					String osuname = inf.getName();
					System.out.print("\n"+"	"+osuname);
					String osufile = osuname;
					fuckfile(osufile,absPath,inf);
				}
			}
		}
		
		
		/*BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ming.txt")));
		String data = null;
		while((data = br.readLine())!=null)
		{
			System.out.println(data); 
		}*/
	
	}

	private static void fuckfile(String osufile,String absPath, File inf) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(absPath + osufile)));
		String line = null;
		ArrayList<String> data = new ArrayList<String>();
		while((line = br.readLine())!=null)
		{
			data.add(line);
			if(line.contains("CircleSize:")||line.contains("CircleSize:"))
				System.out.print(" "+line);
		}
		br.close();
		inf.delete();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(absPath + osufile)));
		for(String newline : data)
		{
			if(newline.contains("CircleSize:"))
				bw.write("CircleSize:5\n");
			else if(newline.contains("ApproachRate:"))
				bw.write("ApproachRate:9\n");
			else bw.write(newline+"\n");
		}
		bw.close();
	}

	private static boolean isOsu(String name) {
		return name.contains(".osu");
	}
}
