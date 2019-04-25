package skills.service.metrics.builders.skills

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import skills.service.controller.result.model.CountItem
import skills.service.datastore.services.AdminUsersService
import skills.service.metrics.ChartParams
import skills.service.metrics.builders.MetricsChartBuilder
import skills.service.metrics.model.ChartOption
import skills.service.metrics.model.ChartType
import skills.service.metrics.model.MetricsChart
import skills.service.metrics.model.Section

@Component('skills-DistinctUsersOverTimeChartBuilder')
@CompileStatic
class DistinctUsersOverTimeChartBuilder implements MetricsChartBuilder {

    static final Integer NUM_DAYS_DEFAULT = 120

    final Integer displayOrder = 0

    @Autowired
    AdminUsersService adminUsersService

    @Override
    Section getSection() {
        return Section.skills
    }

    @Override
    MetricsChart build(String projectId, Map<String, String> props, boolean loadData=true) {
        Integer numDays = ChartParams.getIntValue(props, ChartParams.NUM_DAYS, NUM_DAYS_DEFAULT)
        assert numDays > 1, "Property [${ChartParams.NUM_DAYS}] with value [${numDays}] must be greater than 1"

        String skillId = ChartParams.getValue(props, ChartParams.SECTION_ID)
        assert skillId, "skillId must be specified via ${ChartParams.SECTION_ID} url param"

        List<CountItem> dataItems = (loadData ? adminUsersService.getSubjectUsage(projectId, skillId, numDays) : []) as List<CountItem>

        MetricsChart metricsChart = new MetricsChart(
                chartType: ChartType.Line,
                dataItems: dataItems,
                chartOptions: getChartOptions(),
        )
        return metricsChart
    }

    private Map<ChartOption, Object> getChartOptions() {
        Map<ChartOption, Object> chartOptions = [
                (ChartOption.title)      : 'Distinct # of Users over Time',
                (ChartOption.xAxisType)  : 'datetime',
                (ChartOption.yAxisLabel) : 'Distinct # of Users',
                (ChartOption.dataLabel)  : 'Distinct Users',
        ] as Map<ChartOption, Object>
        return chartOptions
    }
}