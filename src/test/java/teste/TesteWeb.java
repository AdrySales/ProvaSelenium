package teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

class TesteWeb {

	static WebDriver driver;
	private static Document doc;
	private static NodeList ListaProcedimentos;
	private static NodeList ListaCasos;
	private Element caso;
	
	
	
	private String valorElementXml(String tag, int numCasoAtual) throws Exception {
		this.caso=(Element) ListaCasos.item(numCasoAtual);
		NodeList lista=this.caso.getElementsByTagName(tag);
		if(lista.getLength()<=0)
			throw new Exception("tag "+tag+ " não localizada!");
		return lista.item(0).getTextContent();
		
	}
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
	System.setProperty("webdriver.gecko.driver","C:\\Users\\Yasmim Sales\\Pictures\\exe\\geckodriver.exe");
	driver = new FirefoxDriver();
	
	
	File inputFile=new File("src/test/resources/casos_testex.xml");
	DocumentBuilderFactory dbfactory=DocumentBuilderFactory.newInstance();
	DocumentBuilder dBuilder=dbfactory.newDocumentBuilder();
	doc = dBuilder.parse(inputFile);
	doc.getDocumentElement().normalize();
	
	
	ListaProcedimentos=doc.getElementsByTagName("procedimento");

	Element procedimento=(Element) ListaProcedimentos.item(0);
	
	ListaCasos=procedimento.getElementsByTagName("caso");
	}


	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		driver.get("https://www.hoteis.com/profile/signup.html?intlid=HOME+%3A%3A+header_main_section&amp;target=H4sIAAAAAAAAANMHANTS03kBAAAA");
		
	}
	

	@AfterEach
	void tearDown() throws Exception {
		
	}
	

void preencheEmail(String email) {
	driver.findElement(By.id("sign-up-email")).sendKeys(email);}

void preencheSenha(String senha) {
	driver.findElement(By.id("sign-up-password")).sendKeys(senha);}
void preencheNome(String nome) {
	driver.findElement(By.id("sign-up-first-name")).sendKeys(nome);}
void preencheSobrenome(String nome) {
	driver.findElement(By.id("sign-up-last-name")).sendKeys(nome);}

void cadastrar() {
	driver.findElement(By.id("signup-button")).click();
}
			@ParameterizedTest
			@ValueSource(strings= {"0","1","2"})
			void testaSenhaInv(String codCaso) throws Exception {
				
				this.preencheEmail(this.valorElementXml("email",Integer.parseInt(codCaso)));
			    this.preencheNome(this.valorElementXml("nome", Integer.parseInt(codCaso)));
				this.preencheSobrenome(this.valorElementXml("sobrenome", Integer.parseInt(codCaso)));
				this.preencheSenha(this.valorElementXml("senha",Integer.parseInt(codCaso)));
				
				this.cadastrar();
			   
			    assertTrue(driver.findElement(By.id("sign-up-password")).isDisplayed());
			    assertEquals(this.valorElementXml("mensagem", Integer.parseInt(codCaso)) , driver.findElement(By.id("sign-up-password")));
			   
			}
		

	}

	


