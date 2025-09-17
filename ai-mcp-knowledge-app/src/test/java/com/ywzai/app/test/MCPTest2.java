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

import java.time.LocalDateTime;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MCPTest2 {

    @Resource
    private ChatClient chatClient;

    @Resource(name = "syncMcpToolCallbackProvider")
//    @Resource
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
    public void test_WeiXin() {
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

    @Test
    public void test_CPP() {
        log.info("💣💣💣 c++文章发表定时任务被触发了！当前时间：{}", LocalDateTime.now());
        int nowHour = LocalDateTime.now().getHour();
        if (nowHour >= 23 || nowHour < 8) {
            log.info("当前时间 {}点 不在任务执行时间范围内，跳过执行", nowHour);
            return;
        }
        try {
            String userInput = """
                    我需要你帮我生成一篇文章，要求如下；
                    
                    1. 场景为互联网大厂C++求职者面试
                    2. 提问的技术栈如下；
                    
                        核心语言与标准: C++11, C++14, C++17, C++20, STL, Boost
                        构建工具: CMake, Make, Bazel, Ninja
                        框架与库: Qt, Poco, cpprestsdk, gRPC, Protobuf, Thrift
                        数据库与ORM: MySQL Connector/C++, SOCI, ODB, SQLite, PostgreSQL libpqxx
                        测试框架: Google Test, Catch2, Doctest, Google Mock
                        网络与并发: Boost.Asio, ZeroMQ, gRPC, libevent, libuv, pthread, OpenMP, TBB
                        微服务与云原生: gRPC, Thrift, RESTful API, Docker, Kubernetes, Consul, Etcd
                        安全框架: OpenSSL, Botan, libsodium, JWT, OAuth2
                        消息队列: Kafka, RabbitMQ, ZeroMQ, NATS
                        缓存技术: Redis C++ Client(hiredis, redis-plus-plus), Memcached libmemcached
                        日志框架: spdlog, log4cplus, glog, Boost.Log
                        监控与运维: Prometheus C++ Client, Jaeger C++ Client, ELK Stack, Zipkin
                        序列化: Protobuf, Cap’n Proto, FlatBuffers, JSON for Modern C++ (nlohmann/json), cereal
                        CI/CD工具: Jenkins, GitLab CI, GitHub Actions, Bazel Build, Docker, Kubernetes
                        大数据处理: Hadoop C++ APIs, Spark JNI, Flink C++ Gateway, Arrow C++
                        版本控制: Git, SVN
                        工具库: Boost, fmt, nlohmann/json, Eigen, OpenCV, gflags, cURL
                        其他: WebAssembly C++ SDK, WebSocket++, Restbed, LLVM/Clang, Unreal Engine, Cocos2d-x
                    
                    3. 提问的场景方案可包括但不限于；音视频场景,内容社区与UGC,AIGC,游戏与虚拟互动,电商场景,本地生活服务,共享经济,支付与金融服务,互联网医疗,健康管理,医疗供应链,企业协同与SaaS,产业互联网,大数据与AI服务,在线教育,求职招聘,智慧物流,供应链金融,智慧城市,公共服务数字化,物联网应用,Web3.0与区块链,安全与风控,广告与营销,能源与环保。
                    4. 按照故事场景，以严肃的面试官和搞笑的水货程序员谢飞机进行提问，谢飞机对简单问题可以回答出来，回答好了面试官还会夸赞和引导。复杂问题含糊其辞，回答的不清晰。
                    5. 每次进行3轮提问，每轮可以有3-5个问题。这些问题要有技术业务场景上的衔接性，循序渐进引导提问。最后是面试官让程序员回家等通知类似的话术。
                    6. 提问后把问题的答案详细的，写到文章最后，讲述出业务场景和技术点，让小白可以学习下来。
                    
                    根据以上内容，不要阐述其他信息，请直接提供；文章标题（需要含带技术点）、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
                    
                    将以上内容发布文章到CSDN!!!发布到CSDN!!!
                    之后进行，微信公众号消息通知，平台：CSDN、主题：为文章标题、描述：为文章简述、跳转地址：为发布文章到CSDN获取  url,
                    请只返回合法 JSON，字符串里的换行必须写成 \\n。
                    """;

            log.info("执行结果:{},{}", userInput, chatClient.prompt(userInput).call().content());
        } catch (Exception e) {
            log.error("定时任务，回调通知c++文章发表任务失败", e);
        }
    }
}
