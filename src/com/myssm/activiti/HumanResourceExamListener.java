
package com.myssm.activiti;

import java.util.Map;
import java.util.logging.Logger;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * 
 * 
 * 工作流中配置如下：
 * 
 *     <receiveTask id="receivetask2" name="人事面试">
            <extensionElements>
         <activiti:executionListener event="start" class="com.easyway.workflow.activiti.exam.HumanResourceExamListener"/>
      </extensionElements>  
    </receiveTask>
    
 * @author longgangbai
 * 
 * 2011-12-18  上午12:37:01
 */
public class HumanResourceExamListener implements JavaDelegate {
	  private Logger logger=Logger.getLogger(HumanResourceExamListener.class.getName());

	/* (non-Javadoc)
	 * @see org.activiti.engine.delegate.JavaDelegate#execute(org.activiti.engine.delegate.DelegateExecution)
	 */
	@Override
	public void execute(DelegateExecution execute) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		logger.info("检查该考试是否通过开发知识考试....");
		Map<String,Object>  variables= execute.getVariables();
		String reuslt=variables.get("result").toString();
		logger.info("开发知识面试结果"+reuslt);
		logger.info("开始人事面试了....");
		execute.setVariable("result", "该考生开发知识面试通过了....");
		logger.info("人事面试完毕....等候通知....");
	}

}
