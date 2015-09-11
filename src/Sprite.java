import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

public class Sprite {
	private File file;
	private int width;
	private int height;
	private ConvertCmd convertCmd;
	private CompositeCmd compositeCmd;
	
	public Sprite(File file){
		this.file = file;
		convertCmd = ConvertCmdSingleton.getConvertCmdInstance();
		compositeCmd = ConvertCmdSingleton.getCompositeCmdInstance();
	}
	
	public void setupDimension() throws IOException {
		BufferedImage bimg = ImageIO.read(file);
		width = bimg.getWidth();
		height = bimg.getHeight();
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public void process(File backgroundFile) throws IOException, InterruptedException, IM4JavaException{
		trim();
		makeTransparent();
		compositeBackgroud(backgroundFile);
	}
	
	public void trim() throws IOException, InterruptedException, IM4JavaException{
		IMOperation op = new IMOperation();
		op.addImage(file.getAbsolutePath());
		op.trim();
		op.addImage(file.getAbsolutePath());
		convertCmd.run(op);
	}
	
	public void makeTransparent() throws IOException, InterruptedException, IM4JavaException{
		IMOperation op = new IMOperation();
		op.addImage(file.getAbsolutePath());
		op.transparent("white");
		op.addImage(file.getAbsolutePath());
		convertCmd.run(op);
	}
	
	public void compositeBackgroud(File backgroundFile) throws IOException, InterruptedException, IM4JavaException{
		
		IMOperation op = new IMOperation();
		op.gravity("center");
		op.addImage(file.getAbsolutePath());
		op.addImage(backgroundFile.getAbsolutePath());
		op.addImage();
		compositeCmd.run(op,file.getAbsolutePath());
		
		/*
		String cmd ="composite -gravity center "+file.getAbsolutePath()+"  "+backgroundFile.getAbsolutePath()+" "+file.getAbsolutePath();
		imageMagickExecution.execCmd(cmd);
		*/
	}
	
}
