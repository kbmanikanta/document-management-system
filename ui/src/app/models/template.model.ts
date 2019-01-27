import { CompanyModel } from './company.model';
import { TemplateItemModel } from './template-item.model';
import { TemplateState } from './template-state.enum';

export interface TemplateModel {
  id?: number;
  name?: string;
  description?: string;
  state?: TemplateState;
  companyId?: number;
  company?: CompanyModel;
  companyName?: string;
  companyTaxIdNumber?: string;
  templateItems?: TemplateItemModel[];
}
