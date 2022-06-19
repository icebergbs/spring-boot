package com.bingshan.springboot.junit;

public class JunitTest {


    /**
     * 8.1 单元测试的基本原则
     *    宏观上, 单元测试要符合AIR原则
     *    微观上, 单元测试的代码层面要符合 BCDE原则
     *
     *    AIR即空气,单元测试亦是如此.当业务代码在线上运行时,可能感觉不到测试用例的存在价值,但在代码质量的保障上,却非常关键,
     *    新增代码因该同步增加测试用例,修改代码逻辑时也应该同步保证测试用例成功执行.
     *    AIR:
     *          A: Automatic 自动化
     *          I: Independent 独立性
     *          R: Repeatable 可重复
     *
     *    单元测试应该是全自动执行的.如果单元测试的输出结果需要人工介入检查,那么它一定是不合格的.
     *    为保证单元测试稳定可靠且便于维护, 需要保证其独立性.用例之间不允许互相调用,也不允许出现执行次序的先后依赖.
     *
     *    单元测试要保证测试粒度足够小,这样有助于精确定位问题, 单元测试用例默认是方法级别的.单元测试不负责检查跨类或者跨系统的交互逻辑,
     *    那是集成测试需要覆盖的范围.
     *
     *    BCDE:
     *          B: Border, 边界值测试, 包括循环边界\特殊取值\特殊时间点\数据顺序等.
     *          C: Corrent, 正确的输入, 并得到预期的结果.
     *          D: Design, 与设计文档相结合, 来编写单元测试
     *          E: Error, 单元测试的目的是证明程序有错, 而不是程序无错. 为了发现代码中潜在的错误,我们需要在编写测试用例时有一些强制的错误输入
     *                    (如非法数据\异常流程\非业务i允许输入等)来得到预期的错误结果.
     *
     *    由于单元测试只是系统集成测试前的小模块测试有些因素往往是不具备的,因此需要进行Mock, 例如:
     *      1. 功能因素: 比如被测试方法内部调用的功能不可用
     *      2. 时间因素: 比如双是一还没到来, 与此时间相关的功能点.
     *      3. 环境因素: 政策环境,如支付宝政策类新功能; 多端环境, pc\手机等.
     *      4. 数据因素: 线下数据样本过小, 难以覆盖各种线上真实场景.
     *      5. 其他因素: 为了简化测试编写,开发者也可以将一些复杂的依赖采用Mock方式实现.
     *
     *
     * 8.2 单元测试覆盖率
     *     单元测试是一种白盒测试,测试依据程序的内部结构来实现测试代码.
     *     单元测试覆盖率:是指业务代码被单测测试的比例和程度. 各类覆盖率指标从粗到细\从弱到强排列如下:
     *          1. 粗粒度的覆盖
     *              包括类覆盖和方法覆盖两种.
     *              类覆盖是指类中只要有方法或变量被测试用例或执行到
     *              方法同理.只要在测试用例执行过程中, 某个方法被调用了.
     *              从实际场景来看,这个标准是远远不够的.
     *          2. 细粒度的覆盖
     *             包括以下几种:
     *             (1)行覆盖 Line Coverage
     *                  也称为语句覆盖,用来度量可执行的语句是否被执行到.
     *                  执行到的语句行 / 可执行语句行数 = n%
     *                  testMethod()
     */
    public Boolean testMethod(int a, int b, int c) {
        boolean result = false;
        if (a == 1 && b == 2 || c ==3) {
            result = true;
        }
        return result;
    }

    /**
     *                    test: 以上测试用例行覆盖率 100%,但执行过程 c==3的条件没有被执行到, a!-1 b!=2难道不因该被测试一下吗?
     *                    由此可见行覆盖强调并不高,由于容易计算,在主流的覆盖工具中,依然是一个十分常见的参考指标.
     *              (2)分支覆盖 Branch Coverage
     *                  也称为判定覆盖,用来度量程序中每个判定分支是否都被执行到.
     *                  代码被执行到的分支数 / 所以分支总数 = n%
     *              (3)条件判定覆盖 Condition Decision Coverage
     *                  要求设计足够的测试用例,能够让判定中每个条件的所以可能情况至少被执行一次,同时每个判定本身的所以可能结果也至少执行一次.
     *                  testConditonDecisionCoverageTrue(a, b, c)
     *                  testConditonDecisionCoverageFalse()
     *                  通过 @ParameterizedTest,可以定义一个参数化测试, @CsvSource注解使得我们可以通过定义一个String数组来定义多次运行测试时的参数列表.
     *                  判定结果显而易见有两种(true, false) 我们已经都覆盖到了.另外上述三个条件真和假的结果都取道了.因此满足条件判断覆盖
     *              (4)条件组合覆盖 Multiple Condition Coverage
     *                  是指判定中所有条件的各种组合情况都出现至少一次.
     *                  testMultipleConditionCoverageSampleTrue(a, b, c)
     *                  testMultipleConditionCoverageSampleFalse(a, b, c)
     *
     *
     * 8.3 单元测试编写
     *     8.3.1 Junit单元测试框架
     *          Junit5.x有以下三个主要模块组成:
     *              Junit Platform: 用于Jvm上启动测试框架, 统一命令行\Gradle 和Maven等方式执行测试的入口
     *              Junit Jupiter: 包含JUnit5.x全新的编程模型和扩展机制
     *              Junit Vintage: 用于在新的框架中兼容运行Junit3.x 和 Junit4.x 的测试用例
     *          一个典型的测试类结构:
     *              TicketSellerTest
     *          当测试用例较多时,为了更好的组织测试的结构, 推荐使用 @Nested来表达有层次关系的测试用例
     *              TransactionServiceTest
     *              一般不建议使用超过3级的嵌套用例
     *          分组测试和数据驱动测试也是单元测试中十分实用的技巧.
     *              分组测试: 能够实现测试在运行频率维度上的分层. 例如将单元测试分为
     *                  "执行很快且重要" 的冒烟测试用例
     *                  "执行很慢但同样比较重要" 的日常测试用例
     *                  "数据很多但不太重要" 的回归i测试用例
     *                  在不同场景选择性的执行相应的测试用例. 使用@Tag可以很容易实现这种区分.
     *                  TicketSellerTest  通过标签执行的用例类型, 在Maven中通过配置插件来实现
     *                   <plugin>
         *                 <groupId>org.apache.maven.plugins</groupId>
         *                 <artifactId>maven-surefire-plugin</artifactId>
         *                 <version>2.22.2</version>
         *                 <configuration>
         *                     <properties>
         *                         <includeTags>fast</includeTags>
         *                         <excludeTags>slow</excludeTags>
         *                     </properties>
         *                 </configuration>
         *             </plugin>
     *              数据驱动测试: 适用于计算密集型的算法单元,这些功能单元内部逻辑复杂,对于不同的输入得到截然不同的输出.
     *                          使用Junit的 @TestFactory注解能将数据的输入和输出与测试逻辑分开,只需编写一段测试代码,就能一次性对各种类型的输入和输出结果进行验证.
     *
     *    8.3.2 命名
     *          主流命名规范有两种:
     *             1. 传统的以 test开头, 然后加待测试场景和期待结果的命名方式, 例如 "testDecodeUserTokenSuccess"
     *             2. 易于阅读的 shoule...When结构, 例如 "shoudlSuccessWhenDecodeUserToken"
     *             适当精简描述语句长度,通常控制在5个单词内
     *
     *    8.3.3 断言与假设
     *          org.junit.jupiter.api.Assertions 断言
     *          org.junit.jupiter.api.Assumptions 假设
     *          assertEquals assertSame  第一个参数为预期的结果 第二个是实际结果值
     *
     *          AssertJ的最大特点是流式断言 Fluent Assertions
     *
     *
     */

}
