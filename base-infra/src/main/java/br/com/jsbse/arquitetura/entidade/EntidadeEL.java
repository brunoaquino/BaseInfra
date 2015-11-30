package br.com.jsbse.arquitetura.entidade;

import java.io.Serializable;

public abstract class EntidadeEL<ID extends Serializable> extends Entidade<ID> { 
	 private boolean excluido; 
	  
	    public boolean isExcluido() { 
	        return excluido; 
	    } 
	  
	    public void setExcluido(boolean excluido) { 
	        this.excluido = excluido; 
	    } 
}
