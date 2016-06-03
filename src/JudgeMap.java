import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class JudgeMap {
	public static void main(String[] args) throws IOException{
		String relPath = "/Users/tianyang/Desktop/Songs/";
		File file=new File(relPath);
		File[] tempList = file.listFiles();
		ArrayList<beatmap> blist = new ArrayList<beatmap>();
		for(File f : tempList){
			if(!f.isDirectory()) continue;
			String relPath2 = f.getName();
			//System.out.print("\n"+relPath2);
			String absPath = relPath + relPath2 + "/";
			File innerf=new File(absPath);
			File[] innerfiles = innerf.listFiles();
			for(File inf : innerfiles){
				if(isOsu(inf.getName())){
					String osuname = inf.getName();
					//System.out.print("\n"+"	"+osuname);
					String osufile = osuname;
					int score = analysefile(osufile,absPath,inf);
					blist.add(new beatmap(osufile,score));
				}
			}
			Collections.sort(blist);
			for(beatmap b : blist) System.out.println(b.name + " " + b.score);
		}
		
	}
	
	private static int analysefile(String osufile, String absPath, File inf) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(absPath + osufile)));
		String line = null;
		ArrayList<String> data = new ArrayList<String>();
		while((line = br.readLine())!=null)
		{
			//data.add(line);
			if(line.contains("[HitObjects]"))
				break;
		}
		
		while((line = br.readLine())!=null)
		{
			data.add(line);			
		}
		double score = 0;
		
		int start = Integer.valueOf(data.get(0).split(",")[2]);
		int end = Integer.valueOf(data.get(data.size()-1).split(",")[2]);
		double period = end-start;
		period /= 1000;
		
		for(int i = 0; i < data.size()-1;i++){
			String note1 = data.get(i);
			if(data.get(i).contains("|")) continue;
			String[] coords = note1.split(",");
			int x1 = Integer.valueOf(coords[0]);
			int y1 = Integer.valueOf(coords[1]);
			int t1 = Integer.valueOf(coords[2]);
			String note2 = data.get(i+1);//Can potentially be a start of the slider.
			String[] coords2 = note2.split(",");
			int x2 = Integer.valueOf(coords2[0]);
			int y2 = Integer.valueOf(coords2[1]);
			int t2 = Integer.valueOf(coords2[2]);
			double distance = (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
			//distance = Math.sqrt(distance);
			//if(distance < 100) continue;
			if(t2 == t1) {System.out.println("\n==============Not OSU or shitmap==========");return -1;}
			distance /= (t2-t1);
			score += distance;
		}
		score /= period;
		return (int)(score*100);
		
	}

	private static boolean isOsu(String name) {
		return name.contains(".osu");
	}
}
