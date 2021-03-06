package org.serratec.service;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.dto.ProdutoDTO;
import org.serratec.exception.CustomNoContentException;
import org.serratec.exception.CustomNotFoundException;
import org.serratec.exception.ProdutoException;
import org.serratec.dto.ProdutoInserirDTO;
import org.serratec.model.Produto;
import org.serratec.repository.CategoriaRepository;
import org.serratec.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<ProdutoDTO> listar(){
        if(produtoRepository.findAll().isEmpty()){
            throw new CustomNoContentException("");
        }
        List<Produto> produtos = produtoRepository.findAll();     
        return produtos.stream().map(produto -> new ProdutoDTO(produto)).collect(Collectors.toList());
    }
    
    public ProdutoDTO inserir(ProdutoInserirDTO produtoInserirDTO){
        Produto produto = new Produto();
        produto.setNome(produtoInserirDTO.getNome().toUpperCase());
        produto.setValorUnitario(produtoInserirDTO.getValorUnitario());
        produto.setCategoria(categoriaRepository.findByNome(produtoInserirDTO.getCategoria().toUpperCase()));
        produto.setFoto(produtoInserirDTO.getFoto());
        
        if(produtoInserirDTO.getCategoria() == null){
            throw new ProdutoException("Você deve informar a categoria"
            + " a qual deseja relacionar com o produto");
        }
        if(categoriaRepository.findByNome(produtoInserirDTO.getCategoria()) == null){
            throw new ProdutoException("Categoria com nome '" + produtoInserirDTO.getCategoria()
            + "' não encontrada");
        }

        produto = produtoRepository.save(produto);

        return new ProdutoDTO(produto);
    }

    public Produto atualizar(ProdutoInserirDTO produtoInserirDTO, Long id){
        if(produtoRepository.existsById(id)){
            Produto produto = new Produto();
            produto.setId(id);
            produto.setNome(produtoInserirDTO.getNome().toUpperCase());
            produto.setValorUnitario(produtoInserirDTO.getValorUnitario());
            produto.setCategoria(categoriaRepository.findByNome(produtoInserirDTO.getCategoria().toUpperCase()));
            produto.setFoto(produtoInserirDTO.getFoto());

            if(produtoInserirDTO.getCategoria() == null){
                throw new ProdutoException("Você deve informar a categoria"
                + " a qual deseja relacionar com o produto");
            }
            if(categoriaRepository.findByNome(produtoInserirDTO.getCategoria()) == null){
                throw new ProdutoException("Categoria com nome '" + produtoInserirDTO.getCategoria()
                + "' não encontrada");
            }

            return produtoRepository.save(produto);
        }   
		throw new CustomNotFoundException("Produto com id '"+id+"' não encontrado");
    }

    public void deletar(Long id){
        if(produtoRepository.existsById(id)){
            produtoRepository.deleteById(id);
        }else{
            throw new CustomNotFoundException("Produto com id '"+id+"' não encontrado");
        }
    }  
}
