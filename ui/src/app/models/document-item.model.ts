import { TemplateItemModel } from './template-item.model';

export interface DocumentItemModel {
  integerValue?: string;
  doubleValue?: string;
  stringValue?: string;
  booleanValue?: boolean;
  dateValue?: string;
  templateItem?: TemplateItemModel;
  templateItemId?: number;
}
