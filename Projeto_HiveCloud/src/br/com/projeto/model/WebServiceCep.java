package br.com.projeto.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public final class WebServiceCep {

    /* Classes Internas, que auxiliam na busca do CEP */
    /**
     * Enumeration para setar os parametros do XML, cada constante conhece o seu
     * metodo correspondente, invocando a partir de um atalho comum
     */
    private enum Xml {
        CIDADE {
            @Override
            public void setCep(String text, WebServiceCep webServiceCep) {
                webServiceCep.setCidade(text);
            }
        },
        BAIRRO {
            @Override
            public void setCep(String text, WebServiceCep webServiceCep) {
                webServiceCep.setBairro(text);
            }
        },
        TIPO_LOGRADOURO {
            @Override
            public void setCep(String text, WebServiceCep webServiceCep) {
                webServiceCep.setLogradouroType(text);
            }
        },
        LOGRADOURO {
            @Override
            public void setCep(String text, WebServiceCep webServiceCep) {
                webServiceCep.setLogradouro(text);
            }
        },
        RESULTADO {
            @Override
            public void setCep(String text, WebServiceCep webServiceCep) {
                webServiceCep.setResulCode(Integer.parseInt(text));
            }
        },
        RESULTADO_TXT {
            @Override
            public void setCep(String text, WebServiceCep webServiceCep) {
                webServiceCep.setResultText(text);
            }
        },
        UF {
            @Override
            public void setCep(String text, WebServiceCep webServiceCep) {
                webServiceCep.setUf(text);
            }
        };

        /**
         * Seta o texto enviado no parametro text no objeto
         */
        public abstract void setCep(String text, WebServiceCep webServiceCep);
    }

    /**
     * Classe utilitaria apenas encapsula o Iterator de elements da root dentro
     * de um Iterable, para ser usado dentro de um for.
     */
    private static final class IterableElement implements Iterable<Element> {

        private Iterator<Element> itr;

        @SuppressWarnings("unchecked")
        public IterableElement(Iterator<?> itr) {
            this.itr = (Iterator<Element>) itr;
        }

        @Override
        public Iterator<Element> iterator() {
            return itr;
        }
    }

    /**
     * Classe contendo todos os Enums {@link Xml}. Tem como finalidade, buscar
     * um Enumeration especifico pelo seu nome.
     */
    private static final class XmlEnums {

        private HashMap<String, Xml> enumsMap;

        /**
         * Cria um {@link XmlEnums}
         */
        public XmlEnums() {
            initializeEnums();
        }

        /**
         * Inicializa este objeto, guardando os enumerations em um
         * {@link HashMap}
         */
        private void initializeEnums() {
            Xml[] enums = Xml.class.getEnumConstants();
            enumsMap = new HashMap<String, Xml>();
            for (int i = 0; i < enums.length; i++) {
                enumsMap.put(enums[i].name().toLowerCase(), enums[i]);
            }
        }

        /**
         * Busca um Enum {@link Xml} a partir do seu nome, a busca nao é case
         * sensitive, portanto o nome pode ser escrito ignorando lowercases or
         * uppercases.
         */
        public Xml getXml(String xmlName) {
            return enumsMap.get(xmlName.toLowerCase());
        }
    }

    private static final String URL_STRING
            = "http://cep.republicavirtual.com.br/web_cep.php?cep=%s&formato=xml";

    /**
     * Carrega o Documento xml a partir do CEP enviado.
     */
    private static Document getDocument(String cep)
            throws DocumentException, MalformedURLException {
        URL url = new URL(String.format(URL_STRING, cep));
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    /**
     * Retorna o elemento principal (root) da arvore XML.
     */
    private static Element getRootElement(String cep)
            throws DocumentException, MalformedURLException {
        return getDocument(cep).getRootElement();
    }

    /**
     * Encapsula os elementos XML dentro de um objeto
     */
    private static IterableElement getElements(String cep)
            throws DocumentException, MalformedURLException {
        return new IterableElement(getRootElement(cep).elementIterator());
    }

    /**
     * Faz uma busca a partir do cep enviado, no site <a
     * href="http://www.republicavirtual.com.br"
     */
    public static WebServiceCep searchCep(String cep) {
        cep = cep.replaceAll("\\D*", ""); //To numeric digits only
        if (cep.length() > 8) {
            cep = cep.substring(0, 8);
        }
        WebServiceCep loadCep = new WebServiceCep(cep);
        try {
            XmlEnums enums = new XmlEnums();
            for (Element e : getElements(cep)) {
                enums.getXml(e.getQualifiedName()).setCep(e.getText(), loadCep);
            }
        } catch (DocumentException ex) {
            if (ex.getNestedException() != null && ex.getNestedException() instanceof java.net.UnknownHostException) {
                loadCep.setResultText("Site n�o encontrado.");
                loadCep.setResulCode(-14);
            } else {
                loadCep.setResultText("N�o foi possivel ler o documento xml.");
                loadCep.setResulCode(-15);
            }
            loadCep.setExceptio(ex);
        } catch (MalformedURLException ex) {
            loadCep.setExceptio(ex);
            loadCep.setResultText("Erro na forma��o da url.");
            loadCep.setResulCode(-16);
        } catch (Exception ex) {
            loadCep.setExceptio(ex);
            loadCep.setResultText("Erro inesperado.");
            loadCep.setResulCode(-17);
        }
        return loadCep;
    }

    /* Campos internos de resultado da busca */
    private int resulCode = -1;
    private String resultText = "busca nao realizada.";
    private String cep = null;
    private String bairro = null;
    private String cidade = null;
    private String logradouro = null;
    private String logradouroType = null;
    private String uf = null;
    private Exception exception;

    /**
     * Privado para que seja invocado apenas atraves de
     * {@link #searchCep(String)}
     *
     * @param cep
     */
    private WebServiceCep(String cep) {
        this.cep = cep;
    }

    private void setExceptio(Exception ex) {
        this.exception = ex;
    }

    private void setCidade(String cidade) {
        this.cidade = cidade;
    }

    private void setBairro(String bairro) {
        this.bairro = bairro;
    }

    private void setLogradouroType(String logradouroType) {
        this.logradouroType = logradouroType;
    }

    private void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    private void setResulCode(int resultado) {
        this.resulCode = resultado;
    }

    private void setResultText(String resultado_txt) {
        this.resultText = resultado_txt;
    }

    private void setUf(String uf) {
        this.uf = uf;
    }

    public int getResulCode() {
        return resulCode;
    }

    public String getResultText() {
        return resultText;
    }

    /**
     * Informa se o cep foi encontrado com sucesso.
     */
    public boolean wasSuccessful() {
        return (resulCode == 1 && exception == null);
    }

    /**
     * Informa se nao existe o cep cadastrado.
     *
     */
    public boolean isCepNotFound() {
        return (resulCode == 0);
    }

    /**
     * Informa se houve falhas na busca do cep
     *
     */
    public boolean hasException() {
        return (exception != null);
    }

    public Exception getException() {
        return exception;
    }

    /**
     * Informa o bairro
     *
     * @return {@link String} contendo o nome bairro
     */
    public String getBairro() {
        return bairro;
    }

    /**
     * Informa a cidade
     *
     * @return {@link String} contendo o nome da Cidade
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Informa a Unidade Federativa
     *
     * @return {@link String} contendo o nome da Unidade Federativa
     */
    public String getUf() {
        return uf;
    }

    /**
     * Informa o logradouro.
     *
     * @return {@link String} contendo o nome do Logradouro
     */
    public String getLogradouro() {
        return logradouro;
    }

    /**
     * Informa o logradouro junto com o tipo de logradouro.
     *
     * @return {@link String} contendo o tipo de Logradouro + nome do
     * Logradouro.
     */
    public String getLogradouroFull() {
        return (logradouro == null || logradouroType == null) ? null
                : logradouroType + " " + logradouro;
    }

    /**
     * Informa o tipo do logradouro.
     *
     * @return {@link String} contendo o tipo de logradouuro.
     */
    public String getLogradouroType() {
        return logradouroType;
    }

    /**
     * Informa o cep.
     *
     * @return {@link String} contendo o cep.
     */
    public String getCep() {
        return cep;
    }
}
