/**
 * 
 */
package com.share.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.ansj.domain.Term;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.recognition.NatureRecognition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.share.util.CoreUtil;

/**
 * 控制器：中文分词器---Ansj
 *
 * @author user email：ancai0729@gmail.com
 * @since 2012-11-19 上午10:44:32
 * @version 1.0
 */
@Controller
@RequestMapping("/ansj")
public class AnsjDo {
	private static final String ANSJ_TEST_STRING = "Ansj中文分词是一个真正的ict的实现.并且加入了自己的一些数据结构和算法的分词.实现了高效率和高准确率的完美结合!";
	
	/**
	 * 标注的调用方式
	 */
	@ResponseBody
	@RequestMapping(value = "/test1", method = {RequestMethod.GET, RequestMethod.POST})
	public String ansjTest(HttpServletRequest request) {
		String originalCnt = CoreUtil.notNull(request.getParameter("originalCnt")) ? request.getParameter("originalCnt"): ANSJ_TEST_STRING;
		StringBuffer sbuff = new StringBuffer();
		Analysis udf = new ToAnalysis(new StringReader(originalCnt));
		Term term = null;
		try {
			while (null != (term = udf.next())) {
				sbuff.append(term.getName() + "  ");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sbuff.toString();
	}
	
	/**
	 * 简易的调用方式
	 */
	@ResponseBody
	@RequestMapping(value = "/test2", method = {RequestMethod.GET, RequestMethod.POST})
	public List<Term> ansjTest2() {
		List<Term> tempList = new ArrayList<Term>();
		List<Term> termList = ToAnalysis.paser(ANSJ_TEST_STRING);
		for (Term term : termList) {
			tempList.add(new Term(term.getName(), term.getOffe(), term.getTermNatures()));
		}
		
		return tempList;
	}
	
	/**
	 * 词性标注
	 */
	@ResponseBody
	@RequestMapping(value = "/test3", method = {RequestMethod.GET, RequestMethod.POST})
	public List<Term> ansjTest3(@RequestParam("originalCnt") String originalCnt) {
		List<Term> terms = ToAnalysis.paser(originalCnt);
		new NatureRecognition(terms);
		return terms;
	}
}
