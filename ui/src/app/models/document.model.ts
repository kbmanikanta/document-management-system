import { TemplateModel } from './template.model';
import { DocumentItemModel } from './document-item.model';

export interface DocumentModel {
  id?: number;
  name?: string;
  template?: TemplateModel;
  templateId?: number;
  documentItems?: DocumentItemModel[];
  templateName?: string;
}
