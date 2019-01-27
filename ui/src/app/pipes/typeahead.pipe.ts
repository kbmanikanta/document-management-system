import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'typeahead'
})
export class TypeaheadPipe implements PipeTransform {

  transform(options: any[], filterString: string, props: string[]): any {
    if (!options) {
      return;
    }

    const filters = filterString.toLowerCase().split(' ').filter((filter) => filter);
    if (filters.length > 0) {
      return options.filter(option =>
        filters.reduce((acc, filter) => {
          return props.reduce((matches, prop) => {
            return option[prop].toLowerCase().includes(filter) ? ++matches : matches;
          }, 0) > 0 ? ++acc : acc;
        }, 0) === filters.length);
    }

    return options;
  }

}
