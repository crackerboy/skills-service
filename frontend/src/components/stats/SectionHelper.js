export const SECTION = {
  PROJECTS: 'projects',
  BADGES: 'badges',
  SUBJECTS: 'subjects',
  DEPENDENCIES: 'dependencies',
  USERS: 'users',
};

export class SectionParams {
  constructor(builder) {
    this.section = builder.section;
    this.projectId = builder.projectId;
    this.sectionIdParam = builder.sectionIdParam;
    this.chartBuilderId = builder.chartBuilderId;
    this.numMonths = builder.numMonths || 6;
    this.numDays = builder.numDays || 120;
    this.loadDataForFirst = builder.loadDataForFirst || 3;
  }

  static get Builder() {
    class Builder {
      constructor(section, projectId) {
        this.validateSection(section);
        this.section = section;
        this.projectId = projectId;
      }

      withSectionIdParam(sectionIdParam) {
        this.sectionIdParam = sectionIdParam;
        return this;
      }

      withChartBuilderId(chartBuilderId) {
        this.chartBuilderId = chartBuilderId;
        return this;
      }

      withNumMonths(numMonths) {
        this.numMonths = numMonths;
        return this;
      }

      withNumDays(numDays) {
        this.numDays = numDays;
        return this;
      }

      withLoadDataForFirst(loadDataForFirst) {
        this.loadDataForFirst = loadDataForFirst;
        return this;
      }

      validateSection = (section) => {
        switch (section) {
          case SECTION.PROJECTS:
            break;
          case SECTION.BADGES:
            break;
          case SECTION.SUBJECTS:
            break;
          case SECTION.DEPENDENCIES:
            break;
          case SECTION.USERS:
            break;
          default:
            throw new Error(`Invalid Section [${section}]`);
        }
      }

      build() {
        return new SectionParams(this);
      }
    }
    return Builder;
  }
}