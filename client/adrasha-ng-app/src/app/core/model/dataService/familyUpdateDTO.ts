/**
 * Generated by orval v7.10.0 🍺
 * Do not edit manually.
 * ADRASHA DATA-SERVICE API Docs
 * OpenAPI spec version: 1.0.0
 */
import type { FamilyUpdateDTOPovertyStatus } from './familyUpdateDTOPovertyStatus';

export interface FamilyUpdateDTO {
  headMemberId: string;
  ashaId: string;
  houseId?: number;
  povertyStatus?: FamilyUpdateDTOPovertyStatus;
}
