import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.MontageCmd;
import org.json.simple.JSONObject;

public class SpriteFolder {
	private File fileFolder;
	private File directory;
	private int maxWidth = 0;
	private int maxHeight = 0;
	private ConvertCmd convertCmd;
	private MontageCmd montageCmd;
	
	private String destinationFolder = "images";
	private String dataJsonFolder = "data";
	
	private List<Sprite> sprites = new ArrayList<Sprite>();

	public List<Sprite> getSprites(){
		return sprites;
	}
	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}

	public SpriteFolder(File directory, File fileFolder) {
		this.directory = directory;
		this.fileFolder = fileFolder;
		readImageFiles();
		convertCmd = ConvertCmdSingleton.getConvertCmdInstance();
		montageCmd = ConvertCmdSingleton.getMontageCmdInstance();
	}
	
	private void readImageFiles(){
		File spritesFiles[] = fileFolder.listFiles();
		for(File spriteFile : spritesFiles){
			sprites.add(new Sprite(spriteFile));
		}
	}

	public void createDestinationFolder(){
		File folder = new File(destinationFolder);
		if(!folder.exists()){
			folder.mkdir();
		}
		
	}
	
	public void createDataJsonFolder(){
		File folder = new File(dataJsonFolder);
		if(!folder.exists()){
			folder.mkdir();
		}
	}
	
	
	public void montageSpriteSheet() throws IOException, InterruptedException, IM4JavaException {
		String inputImage = fileFolder.getAbsoluteFile()+File.separator+"*.png";
		String destinationImage = destinationFolder+File.separator+fileFolder.getName()+".png";
		
		IMOperation op = new IMOperation();
		op.geometry(maxWidth, maxHeight, 3, 3);
		op.tile("x1");
		op.addImage(inputImage);
		op.addImage();
		
		montageCmd.run(op,destinationImage);
	}

	public void makeTransparent() throws IOException, InterruptedException, IM4JavaException {
		IMOperation op = new IMOperation();
		String imageFinal = destinationFolder+File.separator+fileFolder.getName()+".png"; 
		op.addImage(imageFinal);
		op.transparent("white");
		op.addImage(imageFinal);
		convertCmd.run(op);
		
		//String cmd = "convert images/"+fileFolder.getName()+".png -transparent white images/"+fileFolder.getName()+".png";
		//imageMagickExecution.execCmd(cmd);
	}

	@SuppressWarnings("unchecked")
	public void writeJson() {
		JSONObject obj = new JSONObject();

		JSONObject sprite = new JSONObject();

		sprite.put("sx", 0);
		sprite.put("sy", 0);
		sprite.put("tilew", 6 + maxWidth);
		sprite.put("tileh", 3 + maxHeight);
		sprite.put("cols", sprites.size());
		sprite.put("frames", sprites.size());

		obj.put(fileFolder.getName(), sprite);

		try {

			FileWriter file = new FileWriter(dataJsonFolder + File.separator
					+ fileFolder.getName() + ".json");
			file.write(obj.toJSONString());
			file.flush();
			file.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public File createBackground() throws IOException, InterruptedException, IM4JavaException {
		File backgroudFile = new File(directory.getAbsolutePath()+File.separator+fileFolder.getName()+"_background.png");
		
		IMOperation op = new IMOperation();
		op.size(maxWidth, maxHeight);
		op.addRawArgs("xc:white");
		op.addImage(backgroudFile.getAbsolutePath());
		convertCmd.run(op);
		return backgroudFile;
	}

	public void setupMaxs() {
		for (Sprite sprite : sprites) {
			try {
				sprite.setupDimension();
				setMaxWidthHeight(sprite.getWidth(), sprite.getHeight());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setMaxWidthHeight(int width, int height) {
		if (width > maxWidth) {
			maxWidth = width;
		}
		if (height > maxHeight) {
			maxHeight = height;
		}
	}

}
