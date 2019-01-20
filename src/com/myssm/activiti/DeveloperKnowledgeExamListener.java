
package com.myssm.activiti;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * 
 * 工作流中配置如下：
 *     <receiveTask id="receivetask1" name="笔试以及面试通过">
      <extensionElements>
         <activiti:executionListener event="start" class="com.easyway.workflow.activiti.exam.DeveloperKnowledgeExamListener"/>
      </extensionElements>
    </receiveTask>
    
 * @author longgangbai
 * 
 * 2011-12-18  上午12:38:24
 */
public class DeveloperKnowledgeExamListener implements JavaDelegate {
   private Logger logger=Logger.getLogger(DeveloperKnowledgeExamListener.class.getName());
	/* (non-Javadoc)
	 * @see org.activiti.engine.delegate.JavaDelegate#execute(org.activiti.engine.delegate.DelegateExecution)
	 */
	@Override
	public void execute(DelegateExecution execute) throws Exception {
		// TODO Auto-generated method stub
		logger.info("开始开发知识面试了....");
		Map<String,Object>  variables= execute.getVariables();
		Set<Entry<String,Object>>  infos=variables.entrySet();
		for (Entry<String, Object> entry : infos) {
			logger.info(entry.getKey()+" "+entry.getValue());
		}
		logger.info("开始开发知识面试了....");
		execute.setVariable("result", "该考生开发知识面试通过了....");
		
	}

}
