import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.im4java.core.IM4JavaException;

public class ProcessSheet {
	private List<SpriteFolder> listSpriteFolders = new ArrayList<SpriteFolder>();
	public ProcessSheet(){
	}
	public void readSpriteFolders(String dir){
		File directory = new File(dir);
		File fileSpriteFolders[] = directory.listFiles();
		for(File fileSpriteFolder : fileSpriteFolders){
			if(fileSpriteFolder.isDirectory()){
				listSpriteFolders.add(new SpriteFolder(directory,fileSpriteFolder));
			}
		}
	}
	
	public void process() throws IOException, InterruptedException, IM4JavaException{
		for(SpriteFolder spriteFolder : listSpriteFolders){
			for(Sprite sprite : spriteFolder.getSprites()){
				sprite.makeTransparent();
				sprite.trim();
			}
			spriteFolder.setupMaxs();
			File backgroundFile = spriteFolder.createBackground();
			for(Sprite sprite : spriteFolder.getSprites()){
				sprite.compositeBackgroud(backgroundFile);
				sprite.makeTransparent();
			}
			spriteFolder.montageSpriteSheet();
			spriteFolder.makeTransparent();
			spriteFolder.writeJson();
		}
	}
	
	
	
}
