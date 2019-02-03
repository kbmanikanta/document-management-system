import { TemplateItemType } from '../../../../models/template-item-type.enum';

export class TemplateEditFormService {

  private templateItemTypes: { labelKey: String, value: number }[] = [
    {
      labelKey: TemplateItemType[TemplateItemType.INTEGER],
      value: TemplateItemType.INTEGER
    },
    {
      labelKey: TemplateItemType[TemplateItemType.DOUBLE],
      value: TemplateItemType.DOUBLE
    },
    {
      labelKey: TemplateItemType[TemplateItemType.STRING],
      value: TemplateItemType.STRING
    },
    {
      labelKey: TemplateItemType[TemplateItemType.BOOLEAN],
      value: TemplateItemType.BOOLEAN
    },
    {
      labelKey: TemplateItemType[TemplateItemType.DATE],
      value: TemplateItemType.DATE
    }
  ];

  constructor() {}

  getTemplateItemTypes() {
    return this.templateItemTypes;
  }

}
