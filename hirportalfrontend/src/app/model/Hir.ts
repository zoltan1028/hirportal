import { HirFoOldal } from './HirFoOldal';
import { Kategoria } from './Kategoria';
import { SzerkesztoDtoGet } from './SzerkesztoDtoGet';
export interface Hir {
  id: number|null,
  cim: string,
  lejarat: string,
  szoveg: string,
  letrehozas: string,
  kategoriak: Kategoria[],
  keplink: string,
  isVezercikk: boolean,
  hirFooldal: HirFoOldal,
  szerkesztok: SzerkesztoDtoGet[]
}
