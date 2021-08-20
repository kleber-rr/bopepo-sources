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
 * Created at: 30/03/2008 - 18:04:37
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
 * Criado em: 30/03/2008 - 18:04:37
 * 
 */


package org.jrimum.bopepo;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jrimum.utilix.Objects;
import org.jrimum.utilix.text.AbstractLineOfFields;
import org.jrimum.utilix.text.Field;
import org.jrimum.utilix.text.Strings;
import org.jrimum.vallia.digitoverificador.BoletoLinhaDigitavelDV;


/**
 * 
 * Representa a linha digitÃ¡vel do boleto, embora a linha digitÃ¡vel contenha a
 * mesma informaÃ§Ã£o do cÃ³digo de barras, essa informaÃ§Ã£o Ã© disposta de uma forma
 * diferente e sÃ£o acrescentados 3 dÃ­gitos verificadores. <br />
 * <br />
 * Modelo: <br />
 * <br />
 * 
 * <table border="1" cellpadding="0" cellspacing="0" style="border-collapse:
 * collapse" bordercolor="#111111" width="90%" id="linhaDigitÃ¡vel"> <thead>
 * <tr>
 * <th>PosiÃ§Ã£o </th>
 * <th>Tamanho</th>
 * <th>ConteÃºdo</th>
 * </tr>
 * </thead>
 * <tr>
 * <td>01-03 </td>
 * <td>3 </td>
 * <td>IdentificaÃ§Ã£o do banco </td>
 * </tr>
 * <tr>
 * <td>04-04</td>
 * <td>1 </td>
 * <td>CÃ³digo de moeda (9 â€“ Real) </td>
 * </tr>
 * <tr>
 * <td>05-09 </td>
 * <td>5 </td>
 * <td>Cinco primeiras posiÃ§Ãµes do campo livre (posiÃ§Ãµes 20 a 24 do cÃ³digo de
 * barras)</td>
 * </tr>
 * <tr>
 * <td>10-10 </td>
 * <td>1 </td>
 * <td>DÃ­gito verificador do primeiro campo </td>
 * </tr>
 * <tr>
 * <td>11-20 </td>
 * <td>10 </td>
 * <td>6Âª a 15Âª posiÃ§Ãµes do campo livre (posiÃ§Ãµes 25 a 34 do cÃ³digo de barras)
 * </td>
 * </tr>
 * <tr>
 * <td>21-21 </td>
 * <td>1 </td>
 * <td>DÃ­gito verificador do segundo campo </td>
 * </tr>
 * <tr>
 * <td>22-31 </td>
 * <td>10 </td>
 * <td>16Âª a 25Âª posiÃ§Ãµes do campo livre (posiÃ§Ãµes 35 a 44 do cÃ³digo de barras)
 * </td>
 * </tr>
 * <tr>
 * <td>32-32 </td>
 * <td>1 </td>
 * <td>DÃ­gito verificador do terceiro campo </td>
 * </tr>
 * <tr>
 * <td>33-33 </td>
 * <td>1 </td>
 * <td>DÃ­gito verificador geral (posiÃ§Ã£o 5 do cÃ³digo de barras) </td>
 * </tr>
 * <tr>
 * <td>34-37 </td>
 * <td>4 </td>
 * <td>PosiÃ§Ãµes 34 a 37 â€“ fator de vencimento (posiÃ§Ãµes 6 a 9 do cÃ³digo debarras)</td>
 * </tr>
 * <tr>
 * <td>37-47 </td>
 * <td>10 </td>
 * <td>PosiÃ§Ãµes 38 a 47 â€“ valor nominal do tÃ­tulo(posiÃ§Ãµes 10 a 19 do cÃ³digo de barras) </td>
 * </tr>
 * </table>
 * 
 * <br />
 * <br />
 * 
 * ObservaÃ§Ãµes:
 * 
 * <br />
 * <ul>
 * 
 * <li>Em cada um dos trÃªs primeiros campos, apÃ³s a 5a posiÃ§Ã£o, deve ser
 * inserido um ponto â€œ.â€�, a fim de facilitar a visualizaÃ§Ã£o, para a digitaÃ§Ã£o,
 * quando necessÃ¡rio;</li>
 * <li>Quinto campo:
 * <ul>
 * <br />
 * <li>preenchimento com zeros entre o fator de vencimento e o valor atÃ©
 * completar 14 posiÃ§Ãµes;
 * <li>a existÃªncia de â€œ0000â€� no campo â€œfator de vencimentoâ€� da linha digitÃ¡vel
 * do bloqueto de cobranÃ§a Ã© indicativo de que o cÃ³digo de barras nÃ£o contÃ©m
 * fator de vencimento. Nesse caso, o banco acolhedor/recebedor estarÃ¡ isento
 * das responsabilidades pelo recebimento apÃ³s o vencimento, que impede de
 * identificar automaticamente se o bloqueto estÃ¡ ou nÃ£o vencido;</li>
 * <li>quando se tratar de bloquetos sem discriminaÃ§Ã£o do valor no cÃ³digo de
 * barras, a representaÃ§Ã£o deverÃ¡ ser com zeros;</li>
 * <li>nÃ£o deverÃ¡ conter separaÃ§Ã£o por pontos, vÃ­rgulas ou espaÃ§os;</li>
 * </ul>
 * <br />
 * </li>
 * <li>Os dÃ­gitos verificadores referentes aos 1Âº, 2Âº e 3Âº campos nÃ£o sÃ£o
 * representados no cÃ³digo de barras;</li>
 * <li>Os dados da linha digitÃ¡vel nÃ£o se apresentam na mesma ordem do cÃ³digo
 * de barras.</li>
 * 
 * </ul>
 * 
 * 
 * @see org.jrimum.bopepo.CodigoDeBarras
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
public final class LinhaDigitavel extends AbstractLineOfFields {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6089634012523938802L;
	
	private static Logger log = Logger.getLogger(LinhaDigitavel.class);
	
	/**
	 * 
	 */
	private static final Integer FIELDS_LENGTH = 5;
	
	/**
	 * <p>
	 * Tamanho dos campos mais os espaÃ§os entre eles.
	 * </p>
	 */
	private static final Integer STRING_LENGTH = 54;

	/**
	 * 
	 */
	private Field<InnerCampo1> innerCampo1;
	
	/**
	 * 
	 */
	private Field<InnerCampo2> innerCampo2;
	
	/**
	 * 
	 */
	private Field<InnerCampo3> innerCampo3;
	
	/**
	 * <p>
	 * Digito verificador geral.
	 * </p>
	 */
	private Field<Integer> campo4;
	
	/**
	 * 
	 */
	private Field<InnerCampo5> innerCampo5;


	/**
	 * <p>
	 * Cria uma linha digitÃ¡vel a partir do cÃ³digo de barras passado.
	 * </p>
	 * 
	 * @param codigoDeBarras
	 * 
	 * @see CodigoDeBarras
	 * 
	 * @since 0.2
	 */
	LinhaDigitavel(CodigoDeBarras codigoDeBarras) {
		super(FIELDS_LENGTH,STRING_LENGTH);
		
		if(log.isTraceEnabled())
			log.trace("Instanciando Linha DigitÃ¡vel");
		
		if(log.isDebugEnabled())
			log.debug("codigoDeBarra instance : "+codigoDeBarras);
		
		innerCampo1 = new Field<InnerCampo1>(new InnerCampo1(4,11),11);
		innerCampo2 = new Field<InnerCampo2>(new InnerCampo2(2,12),12);
		innerCampo3 = new Field<InnerCampo3>(new InnerCampo3(2,12),12);
		campo4 = new Field<Integer>(new Integer(0),1);
		innerCampo5 = new Field<InnerCampo5>(new InnerCampo5(2,14),14);
		
		add(innerCampo1);
		add(innerCampo2);
		add(innerCampo3);
		add(campo4);
		add(innerCampo5);
		
		this.innerCampo1.getValue().load(codigoDeBarras);
		this.innerCampo2.getValue().load(codigoDeBarras);
		this.innerCampo3.getValue().load(codigoDeBarras);
		
		this.campo4.setValue(codigoDeBarras.getDigitoVerificadorGeral().getValue());
		
		if(log.isDebugEnabled())
			log.debug("InnerCampo 4 da Linha DigitÃ¡vel : "+this.campo4.getValue());
		
		this.innerCampo5.getValue().load(codigoDeBarras);
		
		if(log.isDebugEnabled() || log.isTraceEnabled())
			log.debug("linhaDigitavel instanciada : "+this.write());
	}

	/**
	 * Escreve a linha digitÃ¡vel foramatada (com espaÃ§o entre os campos).
	 * 
	 * @see org.jrimum.utilix.text.AbstractLineOfFields#write()
	 */
	@Override
	public String write(){
		
		return new StringBuilder(innerCampo1.write()).
		append(Strings.WHITE_SPACE).
		append(innerCampo2.write()).
		append(Strings.WHITE_SPACE).
		append(innerCampo3.write()).
		append(Strings.WHITE_SPACE).
		append(campo4.write()).
		append(Strings.WHITE_SPACE).
		append(innerCampo5.write()).toString();

	}

	private abstract class InnerCampo extends AbstractLineOfFields {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 6746400538765124943L;
		/**
		 * 
		 */
		protected final BoletoLinhaDigitavelDV calculadorDV = new BoletoLinhaDigitavelDV();
		
		
		protected InnerCampo(Integer fieldsLength, Integer stringLength) {
			super(fieldsLength, stringLength);
		}
		
	}
	
	private abstract class InnerCampoFormatado extends InnerCampo {
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 3650450185403697045L;

		protected InnerCampoFormatado(final Integer fieldsLength, final Integer stringLength) {
			super(fieldsLength, stringLength);
		}
		
		/**
		 * <p>
		 * 
		 * AplicaÃ§Ã£o do seguinte requisito da FEBRABAN: <br />
		 * Em cada um dos trÃªs primeiros campos, apÃ³s a quinta (5) posiÃ§Ã£o, deve ser
		 * inserido um ponto â€œ.â€�, a fim de facilitar a visualizaÃ§Ã£o, para a
		 * digitaÃ§Ã£o, quando necessÃ¡rio.
		 * 
		 * </p>
		 * 
		 * 
		 * @see org.jrimum.utilix.text.AbstractLineOfFields#write()
		 */
		@Override
		public String write(){
			
			StringBuilder lineOfFields = new StringBuilder(StringUtils.EMPTY);
			
			for(Field<?> field : this)
				lineOfFields.append(field.write());
			
			lineOfFields.insert(5, ".");
			
			isConsistent(lineOfFields);
			
			return lineOfFields.toString();
		}
		
	}
	
	/**
	 * Componhe o campo 1 da linha digitÃ¡vel com os seguintes dados: <br />
	 * <ul>
	 * <li>IdentificaÃ§Ã£o do banco</li>
	 * <li>CÃ³digo de moeda (9 â€“ Real)</li>
	 * <li>Cinco primeiras posiÃ§Ãµes do campo livre (posiÃ§Ãµes 20 a 24 do cÃ³digo
	 * de barras)</li>
	 * <li>DÃ­gito verificador do primeiro campo</li>
	 * </ul>
	 * 
	 * @param titulo
	 * @param codigoDeBarra
	 * @param calculadorDV
	 */
	private class InnerCampo1 extends InnerCampoFormatado{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 2948116051922000890L;

		/**
		 * @param fieldsLength
		 * @param stringLength
		 */
		private InnerCampo1(Integer fieldsLength, Integer stringLength) {
			super(fieldsLength, stringLength);
		}
		
		/**
		 * @param codigoDeBarras
		 */
		private void load(CodigoDeBarras codigoDeBarras){
				
				if(log.isTraceEnabled())
					log.trace("Compondo campo 1 da Linha DigitÃ¡vel");
				
				add(new Field<String>(codigoDeBarras.write().substring(0, 3),3));
				add(new Field<String>(codigoDeBarras.write().substring(3, 4),1));
				add(new Field<String>(codigoDeBarras.write().substring(19, 24),5));				
				add(new Field<Integer>(calculadorDV.calcule(get(0).write() + get(1).write() + get(2).write()),1));
				
				if(log.isDebugEnabled())
					log.debug("Digito verificador do Field 1 da Linha DigitÃ¡vel : "+get(3).getValue());

				
				if(log.isDebugEnabled() || log.isTraceEnabled())
					log.debug("Field 1 da Linha DigitÃ¡vel composto : "+write());
		}
		
	}
	
	/**
	 * Componhe o campo 2 da linha digitÃ¡vel com os seguintes dados: <br />
	 * <ul>
	 * <li>6Âª a 15Âª posiÃ§Ãµes do campo livre (posiÃ§Ãµes 25 a 34 do cÃ³digo de
	 * barras)</li>
	 * <li>DÃ­gito verificador do segundo campo</li>
	 * </ul>
	 * 
	 * @param codigoDeBarra
	 * @param calculadorDV
	 */
	private class InnerCampo2 extends InnerCampoFormatado{

		/**
		 * 
		 */
		private static final long serialVersionUID = -2201847536243988819L;

		/**
		 * @param fieldsLength
		 * @param stringLength
		 */
		private InnerCampo2(Integer fieldsLength, Integer stringLength) {
			super(fieldsLength, stringLength);
		}
		
		
		/**
		 * @param codigoDeBarras
		 */
		private void load(CodigoDeBarras codigoDeBarras){
			
			if(log.isTraceEnabled())
				log.trace("Compondo campo 2 da Linha DigitÃ¡vel");
			
			add(new Field<String>(codigoDeBarras.write().substring(24, 34),10));				
			add(new Field<Integer>(calculadorDV.calcule(get(0).write()),1));
			
			if(log.isDebugEnabled())
				log.debug("Digito verificador do campo 2 da Linha DigitÃ¡vel : "+get(1).getValue());
			
			if(log.isDebugEnabled() || log.isTraceEnabled())
				log.debug("InnerCampo 2 da Linha DigitÃ¡vel composto : "+write());
		}
		
	}
	
	/**
	 * Componhe o campo 3 da linha digitÃ¡vel com os seguintes dados: <br />
	 * <ul>
	 * <li>16Âª a 25Âª posiÃ§Ãµes do campo livre (posiÃ§Ãµes 35 a 44 do cÃ³digo de
	 * barras)</li>
	 * <li>DÃ­gito verificador do terceiro campo</li>
	 * </ul>
	 * 
	 * @param codigoDeBarra
	 * @param calculadorDV
	 */
	private class InnerCampo3 extends InnerCampoFormatado{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -4248472044788156665L;

		/**
		 * @param fieldsLength
		 * @param stringLength
		 */
		private InnerCampo3(Integer fieldsLength, Integer stringLength) {
			super(fieldsLength, stringLength);
		}
		
		/**
		 * @param codigoDeBarras
		 */
		private void load(CodigoDeBarras codigoDeBarras){
			
			if(log.isTraceEnabled())
				log.trace("Compondo campo 3 da Linha DigitÃ¡vel");
			
			add(new Field<String>(codigoDeBarras.write().substring(34, 44),10));				
			add(new Field<Integer>(calculadorDV.calcule(get(0).write()),1));
			
			if(log.isDebugEnabled())
				log.debug("Digito verificador do campo 3 da Linha DigitÃ¡vel : "+get(1).getValue());
			
			if(log.isDebugEnabled() || log.isTraceEnabled())
				log.debug("InnerCampo 3 da Linha DigitÃ¡vel composto : "+write());
			
		}
		
	}
	
	/**
	 * Componhe o campo 5 da linha digitÃ¡vel com os seguintes dados: <br />
	 * <ul>
	 * <li>PosiÃ§Ãµes 34 a 37 â€“ fator de vencimento (posiÃ§Ãµes 6 a 9 do cÃ³digo de
	 * barras)</li>
	 * <li>PosiÃ§Ãµes 38 a 47 â€“ valor nominal do tÃ­tulo(posiÃ§Ãµes 10 a 19 do
	 * cÃ³digo de barras)</li>
	 * </ul>
	 * 
	 * @param codigoDeBarra
	 */
	private class InnerCampo5 extends InnerCampo{

		/**
		 * 
		 */
		private static final long serialVersionUID = -8040082112684009827L;

		/**
		 * @param fieldsLength
		 * @param stringLength
		 */
		private InnerCampo5(Integer fieldsLength, Integer stringLength) {
			super(fieldsLength, stringLength);
		}
		
		/**
		 * @param codigoDeBarras
		 */
		private void load(CodigoDeBarras codigoDeBarras){
			
			if(log.isTraceEnabled())
				log.trace("Compondo campo 5 da Linha DigitÃ¡vel");
			
			add(new Field<String>(codigoDeBarras.write().substring(5, 9),4));
			add(new Field<String>(codigoDeBarras.write().substring(9, 19),10));
			
			if(log.isDebugEnabled() || log.isTraceEnabled())
				log.debug("InnerCampo 5 da Linha DigitÃ¡vel composto : "+write());
			
		}
		
	}

	@Override
	public String toString() {
		return Objects.toString(this);
	}
}
