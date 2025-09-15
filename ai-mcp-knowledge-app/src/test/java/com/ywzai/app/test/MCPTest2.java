package com.ywzai.app.test;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MCPTest2 {

    @Resource
    private ChatClient chatClient;

    @Autowired
    private ToolCallbackProvider tools;

    @Test
    public void test_tool() {
        String userInput = "有哪些工具可以使用";

        System.out.println("\n>>> QUESTION: " + userInput);
        System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());
    }

    @Test
    public void test_saveArticle() {
        String userInput;
        userInput = """
                我需要你帮我生成一篇文章，要求如下；
                                
                1. 场景为互联网大厂java求职者面试
                2. 面试管提问 Java 核心知识、JUC、JVM、多线程、线程池、HashMap、ArrayList、Spring、SpringBoot、MyBatis、Dubbo、RabbitMQ、xxl-job、Redis、MySQL、Linux、Docker、设计模式、DDD等不限于此的各项技术问题。
                3. 按照故事场景，以严肃的面试官和搞笑的水货程序员谢飞机进行提问，谢飞机对简单问题可以回答，回答好了面试官还会夸赞。复杂问题胡乱回答，回答的不清晰。
                4. 每次进行3轮提问，每轮可以有3-5个问题。这些问题要有技术业务场景上的衔接性，循序渐进引导提问。最后是面试官让程序员回家等通知类似的话术。
                5. 提问后把问题的答案，写到文章最后，最后的答案要详细讲述出技术点，让小白可以学习下来。
                                
                根据以上内容，不要阐述其他信息，请直接提供；文章标题、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
                                
                将以上内容发布文章到CSDN
                """;

        System.out.println("\n>>> QUESTION: " + userInput);
        System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(userInput).call().content());
    }

    @Test
    public void test_weixinNotice() {
        String userInput = """
                我需要你帮我生成一篇文章，要求如下；
                                
                1. 场景为互联网大厂java求职者面试
                2. 面试管提问 Java 核心知识、JUC、JVM、多线程、线程池、HashMap、ArrayList、Spring、SpringBoot、MyBatis、Dubbo、RabbitMQ、xxl-job、Redis、MySQL、Linux、Docker、设计模式、DDD等不限于此的各项技术问题。
                3. 按照故事场景，以严肃的面试官和搞笑的水货程序员谢飞机进行提问，谢飞机对简单问题可以回答，回答好了面试官还会夸赞。复杂问题胡乱回答，回答的不清晰。
                4. 每次进行3轮提问，每轮可以有3-5个问题。这些问题要有技术业务场景上的衔接性，循序渐进引导提问。最后是面试官让程序员回家等通知类似的话术。
                5. 提问后把问题的答案，写到文章最后，最后的答案要详细讲述出技术点，让小白可以学习下来。
                                
                根据以上内容，不要阐述其他信息，请直接提供；文章标题、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
                                
                将以上内容发布文章到CSDN。     
                                
                之后进行，微信公众号消息通知，平台：CSDN、主题：为文章标题、描述：为文章简述、跳转地址：从发布文章到CSDN获取 url
                """;

        System.out.println("\n>>> QUESTION: " + userInput);

        System.out.println("\n>>> ASSISTANT: " + chatClient
                .prompt(userInput)
                .call()
                .content());
    }

    @Test
    public void test_weixinNotice_chatMemory() {

        System.out.println("\n>>> ASSISTANT: " + chatClient
                .prompt("""
                        我需要你帮我生成一篇文章，要求如下；
                                
                1. 场景为互联网大厂java求职者面试
                2. 面试管提问 Java 核心知识、JUC、JVM、多线程、线程池、HashMap、ArrayList、Spring、SpringBoot、MyBatis、Dubbo、RabbitMQ、xxl-job、Redis、MySQL、Linux、Docker、设计模式、DDD等不限于此的各项技术问题。
                3. 按照故事场景，以严肃的面试官和搞笑的水货程序员谢飞机进行提问，谢飞机对简单问题可以回答，回答好了面试官还会夸赞。复杂问题胡乱回答，回答的不清晰。
                4. 每次进行3轮提问，每轮可以有3-5个问题。这些问题要有技术业务场景上的衔接性，循序渐进引导提问。最后是面试官让程序员回家等通知类似的话术。
                5. 提问后把问题的答案，写到文章最后，最后的答案要详细讲述出技术点，让小白可以学习下来。
                                
                根据以上内容，不要阐述其他信息，请直接提供；文章标题、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
                                请确保生成的 JSON 是严格合法的，所有字符串中的换行符必须转义为 \\n，不能包含未转义的控制字符
                将以上内容发布文章到CSDN。
                        """)
                .advisors(advisor -> advisor
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, "1001")
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .call()
                .content());

        System.out.println("\n>>> ASSISTANT: " + chatClient
                .prompt("""
                         之后进行，微信公众号消息通知，平台：CSDN、主题：为文章标题、描述：为文章简述、跳转地址：从发布文章到CSDN获取 url
                         请确保生成的 JSON 是严格合法的，所有字符串中的换行符必须转义为 \\n，不能包含未转义的控制字符
                        """)
                .advisors(advisor -> advisor
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, "1001")
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .call()
                .content());
    }
    @Test
    public void test_WeiXin(){
        System.out.println("\n>>> ASSISTANT: " + chatClient
                .prompt("""
                         发送微信公众号消息通知，平台：CSDN、主题：java面试宝典、描述：Java面试大全、跳转地址: http://blog.csdn.net/qq_35904026/article/details/128782345
                         请确保生成的 JSON 是严格合法的，所有字符串中的换行符必须转义为 \\n，不能包含未转义的控制字符,如果发送失败请分析失败原因
                        """)
                .advisors(advisor -> advisor
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, "1001")
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                .call()
                .content());
    }

}