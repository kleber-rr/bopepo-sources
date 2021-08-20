/*
 * Copyright 2008 JRimum Project
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at http://www.apache.org/licenses/LICENSE-2.0 Unless required by
 * applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * Created at: 30/03/2008 - 18:04:23
 * 
 * ================================================================================
 * 
 * Direitos autorais 2008 JRimum Project
 * 
 * Licenciado sob a LicenÃ§a Apache, VersÃ£o 2.0 ("LICENÃ‡A"); vocÃª nÃ£o pode usar
 * esse arquivo exceto em conformidade com a esta LICENÃ‡A. VocÃª pode obter uma
 * cÃ³pia desta LICENÃ‡A em http://www.apache.org/licenses/LICENSE-2.0 A menos que
 * haja exigÃªncia legal ou acordo por escrito, a distribuiÃ§Ã£o de software sob
 * esta LICENÃ‡A se darÃ¡ â€œCOMO ESTÃ�â€�, SEM GARANTIAS OU CONDIÃ‡Ã•ES DE QUALQUER
 * TIPO, sejam expressas ou tÃ¡citas. Veja a LICENÃ‡A para a redaÃ§Ã£o especÃ­fica a
 * reger permissÃµes e limitaÃ§Ãµes sob esta LICENÃ‡A.
 * 
 * Criado em: 30/03/2008 - 18:04:23
 * 
 * Alterado dia 29/11/2019 - Kleber Cardoso
 */


package org.jrimum.bopepo;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jrimum.bopepo.campolivre.CampoLivre;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.AbstractLineOfFields;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Filler;
import org.jrimum.vallia.digitoverificador.BoletoCodigoDeBarrasDV;


/**
 * <p>
 * Ã‰ um nÃºmero Ãºnico para cada Boleto composto dos seguintes campos:
 * </p>
 * <div>
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * 	collapse" bordercolor="#111111" width="100%"> 
 * <thead bgcolor="#DEDEDE">
 * <tr>
 * <th>PosiÃ§Ã£o </th>
 * <th>Tamanho</th>
 * <th>Picture</th>
 * <th>ConteÃºdo</th>
 * </tr>
 * </thead>
 * <tbody style="text-align:center">
 * <tr>
 * <td>01-03</td>
 * <td>3</td>
 * <td style="text-align:right;padding-right:10px">9(3)</td>
 * <td style="text-align:left;padding-left:10px">IdentificaÃ§Ã£o do banco </td>
 * </tr>
 * <tr>
 * <td>04-04 </td>
 * <td>1 </td>
 * <td style="text-align:right;padding-right:10px">9 </td>
 * <td style="text-align:left;padding-left:10px">CÃ³digo moeda (9-Real) </td>
 * </tr>
 * <tr>
 * <td>05-05 </td>
 * <td>1 </td>
 * <td style="text-align:right;padding-right:10px">9 </td>
 * <td style="text-align:left;padding-left:10px">DÃ­gito verificador do composiÃ§Ã£o de barras (DV) </td>
 * </tr>
 * <tr>
 * <td>06-09 </td>
 * <td>4 </td>
 * <td style="text-align:right;padding-right:10px">9(4)</td>
 * <td style="text-align:left;padding-left:10px">PosiÃ§Ãµes 06 a 09 â€“ fator de vencimento</td>
 * </tr>
 * <tr>
 * <td>10-19</td>
 * <td>10</td>
 * <td style="text-align:right;padding-right:10px">9(08)v99</td>
 * <td style="text-align:left;padding-left:10px">PosiÃ§Ãµes 10 a 19 â€“ valor nominal do tÃ­tulo&nbsp;</td>
 * </tr>
 * <tr>
 * <td>20-44 </td>
 * <td>25 </td>
 * <td style="text-align:right;padding-right:10px">9(25) </td>
 * <td style="text-align:left;padding-left:10px">Field livre â€“ utilizado de acordo com a especificaÃ§Ã£o interna do banco
 * emissor</td>
 * </tr>
 * </tbody>
 * </table>
 * </div>
 * 
 * @author <a href="http://gilmatryx.googlepages.com/">Gilmar P.S.L</a>
 * @author <a href="mailto:misaelbarreto@gmail.com">Misael Barreto</a> 
 * @author <a href="mailto:romulomail@gmail.com">RÃ´mulo Augusto</a>
 * @author <a href="http://www.nordestefomento.com.br">Nordeste Fomento Mercantil</a>
 * 
 * @since 0.2
 * 
 * @version 0.2
 */
public final class CodigoDeBarras extends AbstractLineOfFields{

	/**
	 * 
	 */
	private static final long serialVersionUID = 748913164143978133L;
	
	private static Logger log = Logger.getLogger(CodigoDeBarras.class);
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 6;
	
	/**
	 * 
	 */
	private static final Integer STRING_LENGTH = 44;
	
	/**
	 * CÃ³digo do Banco.
	 */
	private Field<String> codigoDoBanco;
	
	/**
	 * CÃ³digo da moeda usada no boleto.
	 */
	private Field<Integer> codigoDaMoeda;
	
	/**
	 * Mecanismo de autenticaÃ§Ã£o usado no composiÃ§Ã£o de barras.
	 * 
	 * @see org.jrimum.vallia.digitoverificador.BoletoCodigoDeBarrasDV
	 */
	private Field<Integer> digitoVerificadorGeral;
	
	/**
	 * Representa a quantidade de dias decorridos da data base Ã  data de
	 * vencimento do tÃ­tulo.
	 * 
	 * @see FatorDeVencimento#toFator(Date)
	 */
	private Field<Integer> fatorDeVencimento;
	
	/**
	 * Valor do tÃ­tulo.
	 */
	private Field<BigDecimal> valorNominalDoTitulo;
	
	/**
	 * @see org.jrimum.bopepo.campolivre.CampoLivre
	 */
	private Field<String> campoLivre;
	
	/**
	 * <p>
	 * Cria um CÃ³digo de Barras a partir do tÃ­tulo e campo livre passados.
	 * </p>
	 * 
	 * @param titulo
	 * @param campoLivre
	 * 
	 * @see CampoLivre
	 */
	CodigoDeBarras(Titulo titulo, CampoLivre campoLivre) {
		super(FIELDS_LENGTH ,STRING_LENGTH);
		
		if(log.isTraceEnabled())
			log.trace("Instanciando o CodigoDeBarras");
			
		if(log.isDebugEnabled()){
			log.debug("titulo instance : "+titulo);
			log.debug("campoLivre instance : "+campoLivre);
		}

		codigoDoBanco = new Field<String>("0", 3, Filler.ZERO_LEFT );
		codigoDaMoeda = new Field<Integer>(0, 1, Filler.ZERO_LEFT);
		digitoVerificadorGeral = new Field<Integer>(0, 1, Filler.ZERO_LEFT);
		fatorDeVencimento = new Field<Integer>(0, 4, Filler.ZERO_LEFT);
		valorNominalDoTitulo = new Field<BigDecimal>(new BigDecimal(0), 10, Filler.ZERO_LEFT);
		this.campoLivre = new Field<String>(StringUtils.EMPTY, 25);
		
		add(codigoDoBanco);
		add(codigoDaMoeda);
		add(digitoVerificadorGeral);
		add(fatorDeVencimento);
		add(valorNominalDoTitulo);
		add(this.campoLivre);
	
		ContaBancaria contaBancaria = titulo.getContaBancaria();
		this.codigoDoBanco.setValue(contaBancaria.getBanco().getCodigoDeCompensacaoBACEN().getCodigoFormatado());
		
		this.codigoDaMoeda.setValue(titulo.getTipoDeMoeda().getCodigo());
		
		//Was here DigitoVerificador 
		//But wait
		this.calculateAndSetFatorDeVencimento(titulo.getDataDoVencimento());
		
		this.valorNominalDoTitulo.setValue(titulo.getValor().movePointRight(2));
		this.campoLivre.setValue(campoLivre.write());
		
		//Now you can
		this.calculateAndSetDigitoVerificadorGeral();
		
		if(log.isDebugEnabled() || log.isTraceEnabled())
			log.debug("codigoDeBarra instanciado : "+this);
	}
	
	private void calculateAndSetDigitoVerificadorGeral() {
		
		if (log.isTraceEnabled())
			log.trace("Calculando Digito Verificador Geral");

		// Instanciando o objeto irÃ¡ calcular o dÃ­gito verificador do boleto.
		BoletoCodigoDeBarrasDV calculadorDV = new BoletoCodigoDeBarrasDV();

		// Preparando o conjunto de informaÃ§Ãµes que serÃ¡ a base para o cÃ¡lculo
		// do dÃ­gito verificador, conforme normas da FEBRABAN.
		StringBuilder toCalculateDV = new StringBuilder(
				codigoDoBanco.write())
		.append(codigoDaMoeda.write())
		.append(fatorDeVencimento.write())
		.append(valorNominalDoTitulo.write())
		.append(campoLivre.write());
		
		System.out.println("codigoDoBanco: " + codigoDoBanco.write().toString());
		System.out.println("codigoDaMoeda: " + codigoDaMoeda.write().toString());
		System.out.println("fatorDeVencimento: " + fatorDeVencimento.write().toString());
		System.out.println("valorNominalDoTitulo: " + valorNominalDoTitulo.write().toString());
		System.out.println("campoLivre: " + campoLivre.write().toString());
		
		StringBuilder toCodigo = new StringBuilder(codigoDoBanco.write());
		System.out.println("toCalculateDV: " + toCalculateDV.toString());
		

		// Realizando o cÃ¡lculo dÃ­gito verificador e em seguida armazenando 
		// a informaÃ§Ã£o no campo "digitoVerificadorGeral".
		digitoVerificadorGeral.setValue(
				calculadorDV.calcule(toCalculateDV.toString(), toCodigo.toString())
				);

		if (log.isDebugEnabled())
			log.debug("Digito Verificador Geral calculado : "
					+ digitoVerificadorGeral.getValue());
	}

	/**
	 * <p>
	 * Representa a quantidade de dias decorridos da data base Ã  data de
	 * vencimento do tÃ­tulo.
	 * </p>
	 * <p>
	 * Ã‰ o resultado da subtraÃ§Ã£o entre a data do vencimento do tÃ­tulo e a DATA
	 * BASE, fixada em 07.10.1997 (03.07.2000 retrocedidos 1000 dias do inï¿½cio
	 * do processo).
	 * </p>
	 * <p>
	 * Os bloquetos de cobranÃ§a emitidos a partir de primeiro de setembro de
	 * 2000 devem conter essas caracterÃ­sticas, para que quando forem capturados
	 * pela rede bancÃ¡ria, os sistemas faÃ§am a operaÃ§Ã£o inversa, ou seja,
	 * adicionar Ã  data base o fator de vencimento capturado, obtendo, dessa
	 * forma, a data do vencimento do bloqueto.
	 * </p>
	 * 
	 * @param vencimento
	 */
	private void calculateAndSetFatorDeVencimento(Date vencimento) {

		fatorDeVencimento.setValue(
				FatorDeVencimento.toFator(vencimento));
	}

	/**
	 * @return the codigoDoBanco
	 */
	Field<String> getCodigoDoBanco() {
		return codigoDoBanco;
	}

	/**
	 * @param codigoDoBanco the codigoDoBanco to set
	 */
	void setCodigoDoBanco(Field<String> codigoDoBanco) {
		this.codigoDoBanco = codigoDoBanco;
	}

	/**
	 * @return the codigoDaMoeda
	 */
	Field<Integer> getCodigoDaMoeda() {
		return codigoDaMoeda;
	}

	/**
	 * @param codigoDaMoeda the codigoDaMoeda to set
	 */
	void setCodigoDaMoeda(Field<Integer> codigoDaMoeda) {
		this.codigoDaMoeda = codigoDaMoeda;
	}

	/**
	 * @return the digitoVerificadorGeral
	 */
	Field<Integer> getDigitoVerificadorGeral() {
		return digitoVerificadorGeral;
	}

	/**
	 * @param digitoVerificadorGeral the digitoVerificadorGeral to set
	 */
	void setDigitoVerificadorGeral(Field<Integer> digitoVerificadorGeral) {
		this.digitoVerificadorGeral = digitoVerificadorGeral;
	}

	/**
	 * @return the fatorDeVencimento
	 */
	Field<Integer> getFatorDeVencimento() {
		return fatorDeVencimento;
	}

	/**
	 * @param fatorDeVencimento the fatorDeVencimento to set
	 */
	void setFatorDeVencimento(Field<Integer> fatorDeVencimento) {
		this.fatorDeVencimento = fatorDeVencimento;
	}

	/**
	 * @return the valorNominalDoTitulo
	 */
	Field<BigDecimal> getValorNominalDoTitulo() {
		return valorNominalDoTitulo;
	}

	/**
	 * @param valorNominalDoTitulo the valorNominalDoTitulo to set
	 */
	void setValorNominalDoTitulo(Field<BigDecimal> valorNominalDoTitulo) {
		this.valorNominalDoTitulo = valorNominalDoTitulo;
	}

	/**
	 * @return the campoLivre
	 */
	Field<String> getCampoLivre() {
		return campoLivre;
	}

	/**
	 * @param campoLivre the campoLivre to set
	 */
	void setCampoLivre(Field<String> campoLivre) {
		this.campoLivre = campoLivre;
	}

	@Override
	public String toString() {
		return Objects.toString(this);
	}
}
