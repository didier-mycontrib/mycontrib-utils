package org.mycontrib.generic.web.dhjs;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.mycontrib.generic.web.html.extension.HjsDynExtension;
import org.mycontrib.generic.web.html.extension.HjsDynViewExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Servlet implementation class HjsDynServlet
 * 
 * Ce servlet retourne tout le rendu HTML+javascript d'une VUE (partie de page HTML/JSP)
 * Cette vue est automatiquement contruite a partir des :
 *      - annotations @DynInput , @DynOutput , ..... presentes dans la classe renseignee via le parametre "view"
 *      - valeurs du javaBean (de scope=session) dont le nom est extrait de l'annotation @DynView de la classe java
 *      
 *      NB: le package de la classe a prendre en compte coorespond au init-param "defaultDynHjsModelViewPackage" du servlet
 *          
 *          Le code genere par ce servlet a besoin des fichier "verif.js" et "my_ajax.js" a incorporer
 *          dans la partie <head><script language="javascript" src="...js"></script></head> de la page JSP
 *          
 *          Une grande partie du code de ce Servlet repose sur la classe utilitaire "HjsDynUtil" du mame package
 *          Ce code utilise essentiellement l'introspection Java pour automatiser les fonctionnalites du framework.
 *          
 *          
 *          Schema essentiel:
 *                 parametres HTTP avec valeurs ====> peuplement JavaBean (via introspection)
 *                 parametre special "action=" ===> declenchement de la methode de meme nom et annotee via @DynAction
 *                (bean Java + introspection) ===>  arborescence d'ojets de type HjsXxxx  ===> .toHjsString() ==> rendu Html + Js 
 *                
 *                
 *                
 * Variante d'appel/utilisation avec parametre ajax=true:
 * 
 * NB: Ce servlet doit etre appele en mode GET via Ajax 
 *     avec une URL de type 
 *     hjsDynServlet?ajax=true&bean=selectionBean&newHtmlContentFor=productId&categoryId=2
 *     ou :
 *        - hjsDynServlet est l'URL pattern associe au servlet courant ( HjsDynServlet )
 *        - ajax=true pour le mode ajax (demande de rendu partiel / ré-actualisation d'une partie)
 *        - bean=selectionBean est le nom du javaBean stocke en scope=session dans lequel  
 *               il faut recuperer des valeurs pour declencher un rafraichissement ajax
 *        - newHtmlContentFor=productId correspond au nom de la propriete pour laquelle on 
 *          demande des nouvelles valeurs (avec un rendu hml) de facon a reactualiser/rafraichir 
 *          une partie de la page html cote navigateur
 *        - category=2 ou xxx=yyyy ou paramName=paramValue 
 *          correspond a une information de type "nouvelle valeur qui a change pour le bean" 
 *          et qui servira de base au calcul d'une reactualisation de la zone a rafraichir.
 *          
 *      Cet appel ajax au servlet retourne normalement au format "text/xml" un contenu "xhtml" correspondant 
 *      au rendu HTML de la partie de la page a rafraichir:
 *      exemple:  
 *             <select size='1' name='productId' value='0' >
 *					<option value='1'>xxx1</option>
 *					<option value='2'>xxx2</option>
 *					<option value='3'>xxx3</option>
 *		        </select>     
 * 
 */
public class HjsDynServlet extends HttpServlet {
	
	private static Logger logger = LoggerFactory.getLogger(HjsDynServlet.class);
	
	private static final long serialVersionUID = 1L;
	
	private String defaultDynHjsModelViewPackage; //nom par defaut du package java prevu pour comporter les classes avec annotations
	//NB:  defaultDynHjsModelViewPackage doit etre paramétré via un init-param de HsjDynServlet de web.xml !!!
	/*
	 
	 <servlet>
    <description>Servlet generant dynamiquement le contenu d'une vue d'ihm en fonction des annoations @Dyn...</description>
    <display-name>HjsDynServlet</display-name>
    <servlet-name>HjsDynServlet</servlet-name>
    <servlet-class>org.mycontrib.generic.web.dhjs.HjsDynServlet</servlet-class>
    <init-param>
        <description>package au sein dequel seront placees les classes avec @DynView , @Dyn...</description>
        <param-name>defaultDynHjsModelViewPackage</param-name>
        <param-value>org.mycontrib.test.generic.web.dhjs.model_view</param-value></init-param>
  </servlet>
  <servlet-mapping>
    <servlet-name>HjsDynServlet</servlet-name>
    <url-pattern>/hjsDynServlet</url-pattern> <!-- now with ajax=true param -->
  </servlet-mapping>
  
	 
	 */
	

	public HjsDynServlet() {
		super();

	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		defaultDynHjsModelViewPackage = config.getInitParameter("defaultDynHjsModelViewPackage");
	}

	public void destroy() {
		// ...
		super.destroy();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doMyWebJobWithOrWithoutAjax(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doMyWebJobWithOrWithoutAjax(request, response);
	}

	protected void doMyWebJobWithOrWithoutAjax(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ajaxParam = 	request.getParameter("ajax");
		if(ajaxParam==null)
			buildFullPage(request,response);
		else if(ajaxParam!=null && ajaxParam.equals("true"))
			buildPartialPageForRemoteAjax(request,response);
		
	}
	protected void buildFullPage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String view = request.getParameter("view");
		PrintWriter out = response.getWriter();
		String className = defaultDynHjsModelViewPackage + "." + view;
		
		HttpSession session = request.getSession();
		Object modelViewBean = HjsDynUtil.getSessionScopeBeanFromClassName(className, session);
		
		Map<String,String[]> paramsMap = request.getParameterMap();
		HjsDynUtil.populateBeanWithParamsMap(modelViewBean,paramsMap);
		String actionParamValue= request.getParameter("action");
		String statut=HjsDynUtil.executeAction(modelViewBean,actionParamValue);// selon param action
		logger.debug("statut="+statut);
		logger.debug(modelViewBean.toString());
		
		HjsDynViewExt hjsView = HjsDynUtil.getHjsViewFromJavaBean(modelViewBean);
		//System.out.println("hjsView.toHjsString():"+hjsView.toHjsString(modelViewBean));
		out.println(hjsView.toHjsString(modelViewBean)); 
		
		
	}
	
	
	protected void buildPartialPageForRemoteAjax(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		
		String beanName=request.getParameter("bean");
		logger.debug("*** ajax---> beanName ="+beanName);
		Object bean=request.getSession().getAttribute(beanName);
		if(bean!=null)
		  {
		  String propertyName=request.getParameter("newHtmlContentFor");
		  logger.debug("*** ajax---> propertyName ="+propertyName);
		  
		  Map<String,String[]> paramsMap = request.getParameterMap();
		  HjsDynUtil.populateBeanWithParamsMap(bean,paramsMap); // pour reactualiser la valeur d'un (ou +) attribut(s)
		                                                        // servant indirectement a en recalculer/reactualiser d'autres 
		
		  
		  HjsDynExtension hjsElement=HjsDynUtil.getHjsElementFromJavaBeanProperty(bean,propertyName);
		  hjsElement.setInnerAjaxRenderOnly(true); // pour "sans prefixe xxx: ni <span id="..."> </span> englobant"
		  out.println(hjsElement.toHjsString(bean));
		  logger.debug("*** ajax---> xml result ="+hjsElement.toHjsString(bean));
		  }
		else out.println("<div>empty ajax result</div>");
		
	}
	

	

}
