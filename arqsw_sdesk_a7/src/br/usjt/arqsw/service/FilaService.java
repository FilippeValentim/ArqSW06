package br.usjt.arqsw.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.usjt.arqsw.dao.FilaDAO;
import br.usjt.arqsw.entity.Chamado;
import br.usjt.arqsw.entity.Fila;

/**
 *Filippe do Nascimento Valentim	
 * RA 81612333
 * Turma SI3AN-MCA
 *
 */

@Service
public class FilaService {
	private FilaDAO dao;
	
	@Autowired
	public FilaService(FilaDAO dao) {
		this.dao = dao;
	}
	public List<Fila> listarFilas() throws IOException{
		return dao.listarFilas();
	}
	public Fila carregar(int id) throws IOException{
		return dao.carregar(id);
	}
	
	public int novaFila(Fila fila) throws IOException{
		return dao.inserirFila(fila);
	}
	public String excluirFila(Fila fila) throws IOException {
		return dao.excluirFila(fila);
	}
	
	public void atualizar(Fila fila) throws IOException{
		dao.atualizar(fila);
	}
	
	public void gravarImagem(ServletContext servletContext, Fila fila, MultipartFile file)
			throws IOException {
		if (!file.isEmpty()) {
			BufferedImage src = ImageIO.read(new ByteArrayInputStream(file
					.getBytes()));
			String path = servletContext.getRealPath(servletContext	.getContextPath());
			path = path.substring(0, path.lastIndexOf('/'));
			String nomeArquivo = "img"+ fila.getId()+".jpg";
			fila.setImagem(nomeArquivo);
			atualizar(fila);
			File destination = new File(path + File.separatorChar + "img" + File.separatorChar + nomeArquivo);
			if(destination.exists()){
				destination.delete();
			}
			ImageIO.write(src, "jpg", destination);
		}
	}
}
