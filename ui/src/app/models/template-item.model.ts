import { TemplateItemType } from './template-item-type.enum';

export interface TemplateItemModel {
  id?: number;
  label?: string;
  type?: TemplateItemType;
  mandatory?: boolean;
  multiple?: boolean;
}
