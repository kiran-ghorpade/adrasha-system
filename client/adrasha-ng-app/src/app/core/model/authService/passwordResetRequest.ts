/**
 * Generated by orval v7.10.0 🍺
 * Do not edit manually.
 * ADRASHA AUTH-SERVICE API Docs
 * OpenAPI spec version: 1.0.0
 */

export interface PasswordResetRequest {
  /**
   * @minLength 8
   * @maxLength 2147483647
   */
  oldPassword: string;
  /**
   * @minLength 8
   * @maxLength 2147483647
   */
  newPassword: string;
}
